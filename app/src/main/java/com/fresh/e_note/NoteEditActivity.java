package com.fresh.e_note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.fresh.e_note.models.Note;
import com.fresh.e_note.viewmodels.DataBaseManager;

public class NoteEditActivity extends AppCompatActivity  implements View.OnClickListener{
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.note_edit_close).setOnClickListener(this);

        Bundle arguments = getIntent().getExtras();
        if (arguments == null) {
            note = new Note(-1, "", "");
        }
        else {
            note = (Note) arguments.getSerializable(Note.class.getSimpleName());
        }
        ((EditText) findViewById(R.id.note_edit_title)).setText(note.getTitle());
        ((EditText) findViewById(R.id.task_edit_text)).setText(note.getText());

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.note_edit_close || id == R.id.btn_cancel) {
            finish();
        }
        else if (id == R.id.btn_save) {
            fetchData();
            DataBaseManager db = new DataBaseManager(this);
            if (note.getID() == -1) {
                db.addNote(note);
            }
            else {
                db.updateNote(note);
            }
            finish();
        }
    }

    private void fetchData() {
        EditText noteTitle = findViewById(R.id.note_edit_title);
        EditText noteText = findViewById(R.id.task_edit_text);

        note.setTitle(noteTitle.getText().toString());
        note.setText(noteText.getText().toString());
    }
}