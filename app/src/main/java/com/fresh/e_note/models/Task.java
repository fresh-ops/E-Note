package com.fresh.e_note.models;

/**
 * Task class extends {@link Note} class.
 * It has special field start that contains start time of task
 */
public class Task extends Note {
    private Clock start;

    public Task(int id) {
        this(id, "");
    }

    public Task(int id, String title) {
        this(id, title, "");
    }

    public Task(int id, String title, String text) {
        this(id, title, text, null);
    }

    public Task(int id, String title, String text, Clock start) {
        super(id, title, text);
        this.start = start;
    }

    public Clock getStart() {
        return start;
    }

    public void setStart(Clock start) {
        this.start = start;
    }

    @Override
    public int getType() {
        return TASK_TYPE;
    }
}
