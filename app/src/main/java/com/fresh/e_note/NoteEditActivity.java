package com.fresh.e_note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.fresh.e_note.models.Note;
import com.fresh.e_note.viewmodels.DataBaseManager;

public class NoteEditActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.note_edit_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        System.out.println("finish");
        int id = view.getId();
        if (id == R.id.note_edit_close || id == R.id.btn_cancel) {
            finish();
        }
        else if (id == R.id.btn_save) {
            Note note = makeNote();
            if (! note.isEmpty()) {
                DataBaseManager db = new DataBaseManager(this);
                db.addNote(note);
            }
            finish();
        }
    }

    private Note makeNote() {
        EditText noteTitle = findViewById(R.id.note_edit_title);
        EditText noteText = findViewById(R.id.note_edit_text);
        String title = noteTitle.getText().toString();
        String text = noteText.getText().toString();
        return new Note(-1, title, text);
    }
}