package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TasksTest {
    private Tasks testTasks;
    private Task testTask;

    @BeforeEach
    void runBefore() {
        testTasks = new Tasks();
        testTask = new Task(null, 0, null);
    }

    @Test
    void testViewTasks() {
        List<Task> tasks = testTasks.viewTasks();
        tasks.add(testTask);
        assertEquals(tasks, testTasks.viewTasks());
        assertEquals(1, tasks.size());
    }

    @Test
    void testGetTasksToDo() {
        testTask.markAsNotDone();
        testTasks.addTask(testTask);
        List<Task> tasksToDo = testTasks.getTasksToDo();
        assertEquals(1, tasksToDo.size());
        assertEquals(testTask, tasksToDo.get(0));
    }

    @Test
    void testNotifyUserOfDeadline() {

    }

    @Test
    void testGetDoneTasks() {
        Task testTask2 = new Task(null, 0, null);
        testTask.markAsDone();
        testTask2.markAsNotDone();
        testTasks.addTask(testTask);
        testTasks.addTask(testTask2);
        List<Task> doneTasks = testTasks.getDoneTasks();
        assertEquals(1, doneTasks.size());
    }

    @Test
    void testGetTotalTimeRequired() {
        testTask.setTimeRequired(60);
        Task testTask2 = new Task(null, 30, null);
        testTasks.addTask(testTask);
        testTasks.addTask(testTask2);
        assertEquals(90, testTasks.getTotalTimeRequired());
    }

    @Test
    void testAddTask() {
        testTasks.addTask(testTask);
        assertEquals(1, testTasks.viewTasks().size());
        testTasks.addTask(testTask);
        assertEquals(2, testTasks.viewTasks().size());
    }

    @Test
    void testRemoveTask() {
        testTasks.addTask(testTask);
        assertEquals(1, testTasks.viewTasks().size());
        testTasks.removeTask(testTask);
        assertEquals(0, testTasks.viewTasks().size());
    }
}
