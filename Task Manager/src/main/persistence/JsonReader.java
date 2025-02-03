package persistence;

import model.Tasks;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// read tasks data from data stored in Json file.
public class JsonReader {
    private final String source;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    // EFFECTS: constructs a JsonReader that reads data from the specified JSON
    // source file.
    public JsonReader(String source) {
        this.source = source;
    }

    // MODIFIES: this
    // EFFECTS: read Tasks data from Json file and return a Tasks object.
    // Throws an IOException if the input or output is invalid.
    public Tasks read() throws IOException {
        String jsonData = Files.readString(Path.of(source));
        return parseTasks(new JSONObject(jsonData));
    }

    // EFFECTS: parse tasks data from the Json Object and return a Tasks object.
    private Tasks parseTasks(JSONObject jsonObject) {
        Tasks tasks = new Tasks();
        addTasks(tasks, jsonObject.getJSONArray("tasks"));
        return tasks;
    }

    // EFFECTS: parse task data from a Json object and return tasks object. .
    private Task parseTask(JSONObject jsonObject) {
        String name = jsonObject.getString("taskName");
        int timeRequired = jsonObject.getInt("timeRequired");
        LocalDateTime deadline = LocalDateTime.parse(jsonObject.getString("deadline"), formatter);
        Task task = new Task(name, timeRequired, deadline);
        if (jsonObject.getBoolean("isDone")) {
            task.markAsDone();
        }
        return task;
    }

    // MODIFIRS: this.
    // EEFECTS: parse Task from Json Array and add it to Tasks object.
    private void addTasks(Tasks tasks, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            Task task = parseTask(nextTask);
            tasks.addTask(task);
        }
    }
}