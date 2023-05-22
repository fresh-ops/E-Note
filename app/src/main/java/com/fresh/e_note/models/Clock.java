package com.fresh.e_note.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Special class to contain time in 24h format
 */
public class Clock implements Serializable {
    private int hours;
    private int minutes;

    public Clock(String time) {
        if (time.length() == 5) {
            String[] timeParsed = time.split(":");
            this.hours = Integer.parseInt(timeParsed[0]);
            this.minutes = Integer.parseInt(timeParsed[1]);
        }
        else {
            hours = 0;
            minutes = 0;
        }
    }

    public Clock(int hours, int minutes) {
        assert(hours < 24);
        assert(hours >= 0);
        this.hours = hours;
        assert(minutes < 60);
        assert(minutes >= 0);
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%02d:%02d", hours, minutes);
    }
}
