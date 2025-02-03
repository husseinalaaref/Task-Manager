package model;

import java.time.LocalDateTime;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.Duration;

public class Task implements Serializable {
    private String taskName;
    private int timeRequired; // (in minutes)
    private LocalDateTime deadline;
    private boolean isDone;

    // REQUIRES: taskName.length() > 0 && timeRequired > 0;
    // EFFECTS: Create a a new instances of taskName,
    // timerequired, and isDone.
    public Task(String taskName, int timeRequired, LocalDateTime deadline) {
        this.taskName = taskName;
        this.timeRequired = timeRequired;
        this.deadline = deadline;
        this.isDone = false;
    }

    // MODIFIES: this
    // EFFECTS: changes the status of the task to done
    // when it is finished.
    public void markAsDone() {
        this.isDone = true;
    }

    // MODIFIES: this
    // EFFECTS: changes the status of the task to not
    // done when it is not finished yet.
    public void markAsNotDone() {
        this.isDone = false;
    }

    // MODIFIES: this
    // EFFECTS: updates the time(in minutes) required
    // to finish a task to newTime.
    public void updateTimeRequired(int newTime) {
        this.timeRequired = newTime;
    }

    // MODIFIES: this
    // EFFECTS: return taskName for a task.
    public String getTaskName() {
        return this.taskName;
    }

    // MODIFIES: this
    // EFFECTS: update taskName for a task.
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    // MODIFIES: this
    // EFFECTS: return the time(in minutes) needed to finish for a task.
    public int getTimeRequired() {
        return timeRequired;
    }

    // MODIFIES: this
    // EFFECTS: updates the time(in minutes) needed to finish for a task.
    public void setTimeRequired(int timeRequired) {
        this.timeRequired = timeRequired;
    }

    // MODIFIES: this.
    // EFFECTS: returns the task's deadline.
    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    // MODIFIES: this.
    // EFFECTS: update the task's deadline to new deadline.
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    // MODIFIES: this.
    // EFFECTS: return true when the task's deadline is 48 hours
    // ahead. duration.between(LocalDateTime.now(), deadline).toHours = 48
    public boolean isDue() {
        Duration isWithin48Hours = Duration.between(LocalDateTime.now(), this.deadline);
        int isIn48Hours = (int) isWithin48Hours.toHours();
        return (isIn48Hours <= 48 && isIn48Hours > 0);
    }

    // MODIFIES: this
    // EFFECTS: return true is the task was finished
    // and false otherwise.
    public boolean isCompleted() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return taskName;
    }

    // MODIFIES: this.
    // EFFECTS: returns a JSONObject representation of the task.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("taskName", taskName);
        json.put("timeRequired", timeRequired);
        json.put("deadline", deadline);
        json.put("isDone", isDone);
        return json;
    }
}