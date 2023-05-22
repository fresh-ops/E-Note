package com.fresh.e_note.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fresh.e_note.NoteEditActivity;
import com.fresh.e_note.R;
import com.fresh.e_note.TaskEditActivity;
import com.fresh.e_note.models.Note;
import com.fresh.e_note.models.Task;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int CONTEXT_EDIT = 0;
    public static final int CONTEXT_DELETE = 1;

    private final Activity activity;
    private final List<Note> list;
    private int position;

    public NotesAdapter(Activity activity, List<Note> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View itemView;
        switch (viewType) {
            case Note.NOTE_TYPE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_items_list, parent, false);
                holder = new NoteViewHolder(itemView);
                break;
            case Note.TASK_TYPE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_items_list, parent, false);
                holder = new TaskViewHolder(itemView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case Note.NOTE_TYPE:
                NoteViewHolder noteVH = (NoteViewHolder) holder;
                Note currentNote = list.get(position);
                noteVH.titleTV.setText(currentNote.getTitle());
                noteVH.textTV.setText(currentNote.getText());
                noteVH.noteCardLV.setOnClickListener(v -> {
                    Intent intent = new Intent(activity, NoteEditActivity.class);
                    intent.putExtra(Note.class.getSimpleName(), currentNote);
                    activity.startActivity(intent);
                });
                noteVH.noteCardLV.setOnLongClickListener(
                        v -> {
                            setPosition(position);
                            return false;
                        }
                );
                break;
            case Note.TASK_TYPE:
                TaskViewHolder taskVH = (TaskViewHolder) holder;
                Task currentTask = (Task) list.get(position);
                taskVH.titleTV.setText(currentTask.getTitle());
                taskVH.timeTV.setText(currentTask.getStart().toString());
                taskVH.taskCardLV.setOnClickListener(v -> {
                    Intent intent = new Intent(activity, TaskEditActivity.class);
                    intent.putExtra(Task.class.getSimpleName(), currentTask);
                    activity.startActivity(intent);
                });
                taskVH.taskCardLV.setOnLongClickListener(v -> {
                    setPosition(position);
                    return false;
                });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        switch (holder.getItemViewType()) {
            case Note.NOTE_TYPE:
                ((NoteViewHolder) holder).noteCardLV.setOnLongClickListener(null);
                break;
            case Note.TASK_TYPE:
                ((TaskViewHolder) holder).taskCardLV.setOnLongClickListener(null);
                break;
        }
        super.onViewRecycled(holder);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder
    implements View.OnCreateContextMenuListener {
        private final TextView titleTV;
        private final TextView textTV;
        private final LinearLayout noteCardLV;

        public NoteViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.note_title);
            textTV = itemView.findViewById(R.id.note_text);
            noteCardLV = itemView.findViewById(R.id.note_card);
            itemView.setOnCreateContextMenuListener(
                    this
            );
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, NotesAdapter.CONTEXT_EDIT, 0, R.string.edit);
            menu.add(0, NotesAdapter.CONTEXT_DELETE, 0, R.string.delete);
        }
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder
    implements View.OnCreateContextMenuListener {
        private final TextView titleTV;
        private final TextView timeTV;
        private final LinearLayout taskCardLV;

        public TaskViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.task_title);
            timeTV = itemView.findViewById(R.id.task_time);
            taskCardLV = itemView.findViewById(R.id.task_card);
            taskCardLV.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, NotesAdapter.CONTEXT_EDIT, 0, R.string.edit);
            menu.add(0, NotesAdapter.CONTEXT_DELETE, 0, R.string.delete);
        }
    }
}
