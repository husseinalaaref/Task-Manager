package persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import model.Tasks;
import model.Task;

public class JsonReaderTest {

    @org.junit.jupiter.api.Test
    void testReaderNoFileExist() {
        JsonReader reader = new JsonReader("./data/NoSuchFile.json");
        try {
            Tasks testTasks = reader.read();
            fail("IOException expected to be thrrown");
        } catch (IOException e) {

        }
    }

    @org.junit.jupiter.api.Test
    void testReaderEmptyTasks() {
        JsonReader testReader = new JsonReader("./data/testReaderEmptyTasks.json");
        try {
            Tasks testTasks = testReader.read();
            assertEquals(0, testTasks.viewTasks().size());
        } catch (IOException e) {
            fail("Could NOT read from file");
        }
    }

    @org.junit.jupiter.api.Test
    void testReaderGeneralTasks() {
        JsonReader testReader = new JsonReader("./data/testReaderGeneralTasks.json");

        try {
            Tasks testTasks = testReader.read();
            LocalDateTime testDateTime = LocalDateTime.of(2024, 10, 11, 11, 59);
            List<Task> testTasksList = testTasks.viewTasks();
            assertEquals(3, testTasksList.size());
            Task testTask = testTasks.viewTasks().get(0);
            assertEquals("STAT 306", testTask.getTaskName());
            assertEquals(60, testTask.getTimeRequired());
            assertEquals(testDateTime, testTask.getDeadline());
            assertFalse(testTask.isCompleted());
            assertEquals(195, testTasks.getTotalTimeRequired());

        } catch (IOException e) {
            // expected
        }

    }
}
