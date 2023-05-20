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
        db.close();
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
        db.close();
    }

    public void deleteItem(Note note) {
        String table = "";
        switch (note.getType()) {
            case Note.NOTE_TYPE:
                table = NOTES_TABLE;
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
            switch (table) {
                case NOTES_TABLE:
                    int id = Integer.parseInt(cursor.getString(0));
                    String title = cursor.getString(1);
                    String text = cursor.getString(2);

                    if (title.equals("") && text.equals("")) deleteItem(NOTES_TABLE, id);
            }
        }

        cursor.close();
    }

    private String makeTableQuery(String table) {
        switch (table) {
            case NOTES_TABLE:
                return String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s TEXT)", NOTES_TABLE, COLUMN_ID, NOTE_TITLE, NOTE_TEXT);
        }

        return "";
    }
}
