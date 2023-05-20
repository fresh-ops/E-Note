package com.fresh.e_note.fragments.notes_fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fresh.e_note.R;
import com.fresh.e_note.models.Note;
import com.fresh.e_note.viewmodels.DataBaseManager;
import com.fresh.e_note.viewmodels.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {
    private final List<Note> noteList = new ArrayList<>();
    private NotesAdapter adapter;
    private DataBaseManager dataBaseManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        dataBaseManager = new DataBaseManager(getActivity());
        fetchData();
        adapter = new NotesAdapter(noteList);
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);
    }

    private void fetchData() {
        noteList.clear();
        Cursor cursor = dataBaseManager.readData(DataBaseManager.NOTES_TABLE);

        if (cursor == null || cursor.getCount() == 0) return;
        while (cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(0));
            String title = cursor.getString(1);
            String text = cursor.getString(2);
            noteList.add(new Note(id, title, text));
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = -1;
        try {
            position = adapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }

        switch (item.getItemId()) {
            case NotesAdapter.CONTEXT_EDIT:
                break;
            case NotesAdapter.CONTEXT_DELETE:
                Note note = noteList.get(position);
                dataBaseManager.deleteItem(note);
        }

        fetchData();
        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }
}