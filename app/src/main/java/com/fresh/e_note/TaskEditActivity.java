package com.fresh.e_note;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fresh.e_note.models.Clock;
import com.fresh.e_note.models.Task;
import com.fresh.e_note.viewmodels.DataBaseManager;

public class TaskEditActivity extends AppCompatActivity implements View.OnClickListener {

    private Task task;
    private Clock start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.task_edit_close).setOnClickListener(this);
        findViewById(R.id.task_set_time).setOnClickListener(this);

        Bundle arguments = getIntent().getExtras();
        if (arguments == null) {
            task = new Task(-1);
            start = new Clock(0, 0);
        }
        else {
            task = (Task) arguments.getSerializable(Task.class.getSimpleName());
            start = task.getStart();
            ((TextView) findViewById(R.id.task_edit_title)).setText(task.getTitle());
            ((TextView) findViewById(R.id.task_edit_text)).setText(task.getText());
            ((Button) findViewById(R.id.task_set_time)).setText(task.getStart().toString());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.task_edit_close || id == R.id.btn_cancel) {
            finish();
        }
        else if (id == R.id.btn_save) {
            fetchData();
            DataBaseManager db = new DataBaseManager(this);
            if (task.getID() == -1) {
                db.addNote(task);
            }
            else {
                db.updateNote(task);
            }
            finish();
        }
        else if (id == R.id.task_set_time) {
            TimePickerDialog.OnTimeSetListener listener = (view1, hourOfDay, minute) -> {
                start.setHours(hourOfDay);
                start.setMinutes(minute);
                ((Button) findViewById(R.id.task_set_time)).setText(start.toString());
            };

            TimePickerDialog timePicker = new TimePickerDialog(this, listener, start.getHours(), start.getMinutes(), true);
            timePicker.setTitle(R.string.set_time);
            timePicker.show();
        }
    }

    private void fetchData() {
        EditText taskTitle = findViewById(R.id.task_edit_title);
        EditText taskText = findViewById(R.id.task_edit_text);

        task.setTitle(taskTitle.getText().toString());
        task.setText(taskText.getText().toString());
        task.setStart(start);
    }
}