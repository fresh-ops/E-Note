package com.fresh.e_note.models;


import java.io.Serializable;

/**
 * Basic Note class to contain notes title and text
 */
public class Note implements Serializable {
    public static final int NOTE_TYPE = 0;
    public static final int TASK_TYPE = 1;
    public static final int TIMETABLE_ITEM_TYPE = 2;

    private String title;
    private String text;

    /**
     * Note class constructor
     * creates new Note instance with empty title and text
     */
    public Note() {
        this(null);
    }

    /**
     * Note class constructor
     * creates new Note instance with title only
     * @param title title of note
     */
    public Note(String title) {
        this(title, null);
    }

    /**
     * Note class constructor
     * creates new Note instance with title and text
     * @param title title of note
     * @param text text of note
     */
    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

    /**
     * Note title getter
     * @return title of note
     */
    public String getTitle() {
        return title;
    }

    /**
     * Note text getter
     * @return text of note
     */
    public String getText() {
        return text;
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
}
