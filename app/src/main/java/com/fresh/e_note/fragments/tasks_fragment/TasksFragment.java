package com.fresh.e_note.fragments.tasks_fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fresh.e_note.R;
import com.fresh.e_note.TaskEditActivity;
import com.fresh.e_note.models.Clock;
import com.fresh.e_note.models.Note;
import com.fresh.e_note.models.Task;
import com.fresh.e_note.viewmodels.DataBaseManager;
import com.fresh.e_note.viewmodels.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {
    private final List<Note> taskList = new ArrayList<>();
    private NotesAdapter adapter;
    DataBaseManager dataBaseManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dataBaseManager = new DataBaseManager(getActivity());
        fetchData();
        adapter = new NotesAdapter(getActivity(), taskList);

        RecyclerView recyclerView = view.findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position;
        try {
            position = adapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }

        switch (item.getItemId()) {
            case NotesAdapter.CONTEXT_EDIT:
                editTask(position);
                break;
            case NotesAdapter.CONTEXT_DELETE:
                Task task = (Task) taskList.get(position);
                dataBaseManager.deleteItem(task);
        }

        fetchData();
        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        fetchData();
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    private void fetchData() {
        taskList.clear();
        dataBaseManager.cleanTable(DataBaseManager.TASKS_TABLE);
        Cursor cursor = dataBaseManager.readData(DataBaseManager.TASKS_TABLE);

        if (cursor == null || cursor.getCount() == 0) return;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String text = cursor.getString(2);
            Clock start = new Clock(cursor.getString(3));
            taskList.add(new Task(id, title, text, start));
        }

        cursor.close();
    }

    private void editTask(int position) {
        Intent intent = new Intent(getContext(), TaskEditActivity.class);
        Task task = (Task) taskList.get(position);
        intent.putExtra(Task.class.getSimpleName(), task);
        startActivity(intent);
    }
}