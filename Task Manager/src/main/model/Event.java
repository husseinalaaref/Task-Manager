package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents a task event.
 */
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private final String description;
    private Date timestamp;

    // construct a task event.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
        this.timestamp = new Date();
    }

    // return timestamp for task.
    public String getTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        return formatter.format(timestamp);
    }

    /**
     * Gets the date of this task (includes time).
     * 
     * @return the date the task is completed.
     */
    public Date getDate() {
        return dateLogged;
    }

    /**
     * Gets the description of this task event.
     * 
     * @return the description of the task event
     */
    public String getDescription() {
        return description;
    }

    // EFFECTS: return true if the task events have the same datelgged
    // and description.
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged) 
                && this.description.equals(otherEvent.description));
    }

    // EFFECTS: rturn a integer represnting the location of the dateloged
    // and description is stored at in memory.
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: returns a String containging the datelogged, converted
    // from Date to String, and the description of the task event.
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
