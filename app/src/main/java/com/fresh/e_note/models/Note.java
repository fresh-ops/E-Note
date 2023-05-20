package com.fresh.e_note.models;


import java.io.Serializable;

/**
 * Basic Note class to contain notes title and text
 */
public class Note implements Serializable {
    public static final int NOTE_TYPE = 0;
    public static final int TASK_TYPE = 1;
    public static final int TIMETABLE_ITEM_TYPE = 2;

    private final int ID;

    private String title;
    private String text;

    /**
     * Note class constructor
     * creates new Note instance with empty title and text
     */
    public Note(int id) {
        this(id,null);
    }

    /**
     * Note class constructor
     * creates new Note instance with title only
     * @param title title of note
     */
    public Note(int id, String title) {
        this(id, title, null);
    }

    /**
     * Note class constructor
     * creates new Note instance with title and text
     * @param title title of note
     * @param text text of note
     */
    public Note(int id, String title, String text) {
        this.ID = id;
        this.title = title;
        this.text = text;
    }

    /**
     * Note title getter
     * @return title of note or empty string if title is null
     */
    public String getTitle() {
        return title == null ? "" : title;
    }

    /**
     * Note text getter
     * @return text of note or empty string if text is null
     */
    public String getText() {
        return text == null ? "" : text;
    }

    /**
     * Note type getter
     * @return type of the note
     */
    public int getType() {
        return NOTE_TYPE;
    }

    /**
     * Note title setter
     * if title param is null, doesn't set new title
     * @param title new title value
     */
    public void setTitle(String title) {
        if (title == null) return;
        if (title.equals(this.title)) return;
        this.title = title;
    }

    /**
     * Note text setter
     * if text param is null, doesn't set new text
     * @param text new text value
     */
    public void setText(String text) {
        if (text == null) return;
        if (text.equals(this.text)) return;
        this.text = text;
    }

    public boolean isEmpty() {
        boolean hasTitle = !((title == null) || title.isEmpty());
        boolean hasText = !((text == null) || text.isEmpty());
        return !(hasTitle || hasText);
    }

    public int getID() {
        return ID;
    }
}
