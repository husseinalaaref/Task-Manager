package persistence;

import model.Tasks;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

// represent a Writer that Write Tasks data to Json destination file.  
public class JsonWriter {
    private final String destination;
    private PrintWriter writer;

    // MODIFIES: this.
    // EFFECTS: set the path of the destination file.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this.
    // EFFECTS: write a representation of tasks data to destination file.
    // throws an IOException if the tasks date writen to Json file is invalid.
    public void write(Tasks tasks) throws IOException {
        JSONObject json = tasks.toJson();
        Files.writeString(Path.of(destination), json.toString(4));
    }

    // MODIFIES: this
    // EFFECTS: opens writer. throws FileNotFoundException if destination file
    // cannot
    // be opened for writing.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: closes writer.
    public void close() {
        writer.close();
    }
}
