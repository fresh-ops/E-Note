package com.fresh.e_note.viewmodels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fresh.e_note.models.Note;

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "userData";
    private static final int DATA_BASE_VERSION = 1;

    public static final String NOTES_TABLE = "notesTable";
    private static final String COLUMN_ID = "id";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_TEXT = "text";

    public DataBaseManager(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = makeTableQuery(NOTES_TABLE);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        onCreate(db);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        switch (note.getType()) {
            case Note.NOTE_TYPE:
                cv.put(NOTE_TITLE, note.getTitle());
                cv.put(NOTE_TEXT, note.getText());
                break;
        }

        db.insert(NOTES_TABLE, null, cv);
    }

    @Nullable
    public Cursor readData(String table) {
        String query = "";
        switch (table) {
            case NOTES_TABLE:
                query = "SELECT * FROM " + NOTES_TABLE;
                break;
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

        switch (note.getType()) {
            case Note.NOTE_TYPE:
                cv.put(NOTE_TITLE, note.getTitle());
                cv.put(NOTE_TEXT, note.getText());
                String id = Integer.toString(note.getID());
                db.update(NOTES_TABLE, cv, "id=?", new String[]{id});
                break;
        }
    }

    public void deleteItem(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        String id = Integer.toString(note.getID());

        switch (note.getType()) {
            case Note.NOTE_TYPE:
                db.delete(NOTES_TABLE, "id=?", new String[]{id});
                break;
        }
    }

    public void clearTable(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + tableName;
        db.execSQL(query);
    }

    private String makeTableQuery(String tableName) {
        switch (tableName) {
            case NOTES_TABLE:
                return String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s TEXT)", NOTES_TABLE, COLUMN_ID, NOTE_TITLE, NOTE_TEXT);
        }

        return "";
    }
}
