package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

public class Tasks extends Task implements Writable {
    private List<Task> tasks;

    // EFFECTS: create instances of ListOfTasks.
    public Tasks() {
        super("CPSC 210 Lab 5", 60, LocalDateTime.of(2024, 10, 11, 11, 59));
        this.tasks = new ArrayList<Task>();
    }
    

    // MODIFIES: this.
    // EFFECTS: return the list of tasks.
    public List<Task> viewTasks() {
        EventLog.getInstance().logEvent(new Event("All tasks viewed."));
        return this.tasks;
    }

    // MODIFIES: this.
    // EFFECTS: returns a list of tasks with task.isDone() == false.
    public List<Task> getTasksToDo() {
        List<Task> toDoTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                toDoTasks.add(task);
            }
        }
        return toDoTasks;
    }

    // MODIFIES: this.
    // EFFECTS: Notify user when task deadline <= 48 hours.
    public void notifyUserOfDeadline() {
        for (Task task : this.tasks) {
            if (task.isDue()) {
                System.out.println(
                        "URGENT REMINDER: the following task is due within 48 hours:\r\n" + task.getTaskName());
            }
        }
    }

    // MODIFIES: this.
    // EFFECTS: returns a list of tasks with task.isDone() == true.
    public List<Task> getDoneTasks() {
        List<Task> doneTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                doneTasks.add(task);
            }
        }
        return doneTasks;
    }

    // MODIFIES: this.
    // EFFECTS: returns the total time required for all the tasks in the list of
    // tasks.
    public int getTotalTimeRequired() {
        return tasks.stream().mapToInt(Task::getTimeRequired).sum();
    }

    // MODIFIES: this.
    // EFFECTS: Add a task to the list of tasks and log the event.
    public void addTask(Task task) {
        tasks.add(task);
        EventLog.getInstance().logEvent(new Event("Task added: " + task.getTaskName()));
        System.out.println("Event logged: Task added: " + task.getTaskName());
    }

    // MODIFIES: this.
    // EFFECTS: Remove a task from the list of tasks and log the event if
    // successful.
    public boolean removeTask(Task task) {
        for (Task task1 : tasks) {
            if (task1.getTaskName().equals(task.getTaskName())) {
                tasks.remove(task);
                EventLog.getInstance().logEvent(new Event("Task removed: " + task.getTaskName()));
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this.
    // EFFECTS: Save tasks to file and log the event.
    public void saveTasks() {
        EventLog.getInstance().logEvent(new Event("Tasks saved."));
    }

    // MODIFIES: this.
    // EFFECTS: Load tasks from file and log the event.
    public void loadTasks() {
        EventLog.getInstance().logEvent(new Event("Tasks loaded."));
    }

    // MODIFIES: this.
    // EFFECTS: returns a JSONObject representation of the Tasks, including an array
    // of tasks.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", tasksToJson());
        return json;
    }

    // MODIFIES: this.
    // EFFECTS: returns a JSONArray representation of the tasks.
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Task task : tasks) {
            jsonArray.put(task.toJson());
        }
        return jsonArray;
    }
}
