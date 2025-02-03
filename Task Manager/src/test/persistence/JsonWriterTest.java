package persistence;

import org.junit.jupiter.api.Test;
import model.Task;
import model.Tasks;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testWriterNoFileExist() {
        try {
            Tasks testTasks = new Tasks();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected to be thrown");
        } catch (IOException e) {

        }
    }

    @Test
    void testWriterEmptyTasks() {
        try {

            JsonWriter testWriter = new JsonWriter("./data/testWriterEmptyTasks.json");
            Tasks testTasks = new Tasks();
            testWriter.open();
            testWriter.write(testTasks);
            testWriter.close();

            JsonReader testReader = new JsonReader("./data/testWriterEmptyTasks.json");
            testTasks = testReader.read();
            List<Task> testTasksList = testTasks.viewTasks();
            assertEquals(0, testTasksList.size());

        } catch (IOException e) {
            fail("No exception should have been thrown!");
        }
    }

    @Test
    void testWriterGeneralTasks() {
        try {
            LocalDateTime testDateTime = LocalDateTime.of(2024, 10, 10, 11, 59);

            Tasks testTasks = new Tasks();
            Task testTask1 = new Task("MATH 344", 120, testDateTime);
            Task testTask2 = new Task("STAT 302", 90, testDateTime);
            Task testTask3 = new Task("CHEM 121", 180, testDateTime);
            Task testTask4 = new Task("STAT 306", 100, testDateTime);

            testTask3.markAsDone();
            testTasks.addTask(testTask1);
            testTasks.addTask(testTask2);
            testTasks.addTask(testTask3);
            testTasks.addTask(testTask4);

            JsonWriter testWriter = new JsonWriter("./data/testWriterGeneralTasks.json");
            testWriter.open();
            testWriter.write(testTasks);
            testWriter.close();

            JsonReader testReader = new JsonReader("./data/testWriterGeneralTasks.json");
            testTasks = testReader.read();
            List<Task> testTasksList = testTasks.viewTasks();
            assertEquals(4, testTasksList.size());
            assertEquals("MATH 344", testTasksList.get(0).getTaskName());
            assertEquals("STAT 302", testTasksList.get(1).getTaskName());
            assertTrue(testTasksList.get(2).isCompleted());
            assertEquals(100, testTasksList.get(3).getTimeRequired());

        } catch (IOException e) {
            fail("No exception should have been thrown!");
        }
    }
}
