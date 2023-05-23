package com.fresh.e_note.viewmodels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fresh.e_note.models.Note;
import com.fresh.e_note.models.Task;

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "userData";
    private static final int DATA_BASE_VERSION = 1;

    public static final String NOTES_TABLE = "notesTable";
    private static final String NOTE_ID = "id";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_TEXT = "text";

    public static final String TASKS_TABLE = "tasksTable";
    private static final String TASK_ID = "id";
    private static final String TASK_TITLE = "title";
    private static final String TASK_TEXT = "text";
    private static final String TASK_START = "start";

    public DataBaseManager(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = makeTableQuery(NOTES_TABLE);
        db.execSQL(query);
        query = makeTableQuery(TASKS_TABLE);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);
        onCreate(db);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        String table = "";

        switch (note.getType()) {
            case Note.NOTE_TYPE:
                cv.put(NOTE_TITLE, note.getTitle());
                cv.put(NOTE_TEXT, note.getText());
                table = NOTES_TABLE;
                break;
            case Note.TASK_TYPE:
                Task task = (Task) note;
                cv.put(TASK_TITLE, task.getTitle());
                cv.put(TASK_TEXT, task.getText());
                if (task.getStart() != null) {
                    cv.put(TASK_START, task.getStart().toString());
                }
                else {
                    cv.put(TASK_START, "");
                }
                table = TASKS_TABLE;
                break;
        }

        db.insert(table, null, cv);
        db.close();
    }

    @Nullable
    public Cursor readData(String table) {
        String query = "";
        switch (table) {
            case NOTES_TABLE:
                query = "SELECT * FROM " + NOTES_TABLE;
                break;
            case TASKS_TABLE:
                query = "SELECT * FROM " + TASKS_TABLE;
        }
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        String id = Integer.toString(note.getID());

        switch (note.getType()) {
            case Note.NOTE_TYPE:
                cv.put(NOTE_TITLE, note.getTitle());
                cv.put(NOTE_TEXT, note.getText());
                db.update(NOTES_TABLE, cv, "id=?", new String[]{id});
                break;
            case Note.TASK_TYPE:
                Task task = (Task) note;
                cv.put(TASK_TITLE, task.getTitle());
                cv.put(TASK_TEXT, task.getText());
                cv.put(TASK_START, task.getStart().toString());
                db.update(TASKS_TABLE, cv, "id=?", new String[]{id});
        }
        db.close();
    }

    public void deleteItem(Note note) {
        String table = "";
        switch (note.getType()) {
            case Note.NOTE_TYPE:
                table = NOTES_TABLE;
                break;
            case Note.TASK_TYPE:
                table = TASKS_TABLE;
                break;
        }
        deleteItem(table, note.getID());
    }

    public void deleteItem(String table, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String index = Integer.toString(id);
        db.delete(table, "id=?", new String[]{index});
        db.close();
    }

    public void cleanTable(String table) {
        Cursor cursor = readData(table);
        if (cursor == null || cursor.getCount() == 0) return;

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String text = cursor.getString(2);
            if (title.equals("") && text.equals("")) deleteItem(table, id);
        }

        cursor.close();
    }

    private String makeTableQuery(String table) {
        switch (table) {
            case NOTES_TABLE:
                return String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s TEXT)", NOTES_TABLE, NOTE_ID, NOTE_TITLE, NOTE_TEXT);
            case TASKS_TABLE:
                return String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%S TEXT, %s TEXT, %s TEXT)", TASKS_TABLE, TASK_ID, TASK_TITLE, TASK_TEXT, TASK_START);
        }

        return "";
    }
}
