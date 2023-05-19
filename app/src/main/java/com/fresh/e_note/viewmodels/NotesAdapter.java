package com.fresh.e_note.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fresh.e_note.R;
import com.fresh.e_note.models.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Note> list;

    public NotesAdapter(List<Note> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case Note.NOTE_TYPE:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_items_list, parent, false);
                holder = new NoteViewHolder(itemView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case Note.NOTE_TYPE:
                Note currentNote = list.get(position);
                ((NoteViewHolder) holder).titleTV.setText(currentNote.getTitle());
                ((NoteViewHolder) holder).textTV.setText(currentNote.getText());
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

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTV;
        private final TextView textTV;

        public NoteViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.note_title);
            textTV = itemView.findViewById(R.id.note_text);
        }
    }
}
