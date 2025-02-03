package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {
    private String testTaskName;
    private int testimeRequired; // in minutes
    private Task testTask;

    @BeforeEach
    void runBefore() {
        testTask = new Task(testTaskName, testimeRequired, null);
    }

    @Test
    void testTask() {
        LocalDateTime testDeadline = LocalDateTime.of(LocalDate.of(2024, 11, 24), LocalTime.of(11, 59));
        testTask = new Task("STAT 301 Project 1", 180, testDeadline);
        assertEquals("STAT 301 Project 1", testTask.getTaskName());
        assertEquals(testDeadline, testTask.getDeadline());
    }

    @Test
    void testMarkAsDone() {
        assertFalse(testTask.isCompleted());
        testTask.markAsDone();
        assertTrue(testTask.isCompleted());
    }

    @Test
    void testUpdateTimeRequired() {
        assertEquals(0, testTask.getTimeRequired());
        testTask.updateTimeRequired(120);
        assertEquals(120, testTask.getTimeRequired());
    }

    @Test
    void testGetTaskName() {
        testTask.setTaskName("CPSC 210 Lab 5");
        assertEquals("CPSC 210 Lab 5", testTask.getTaskName());
    }

    @Test
    void testSetTaskName() {
        testTask.setTaskName("MATH 302 HW 1");
        assertEquals("MATH 302 HW 1", testTask.getTaskName());
    }

    @Test
    void testGetTimeRequired() {
        testTask.setTimeRequired(30);
        assertEquals(30, testTask.getTimeRequired());
    }

    @Test
    void testSetTimeRequired() {
        assertEquals(0, testTask.getTimeRequired());
        testTask.setTimeRequired(30);
        assertEquals(30, testTask.getTimeRequired());
    }

    @Test
    void testGetDeadline() {
        LocalDateTime testDeadline = LocalDateTime.of(LocalDate.of(2024, 10, 11), LocalTime.of(11, 59));
        testTask.setDeadline(testDeadline);
        assertEquals(testDeadline, testTask.getDeadline());
    }

    @Test
    void testSetDeadline() {
        LocalDateTime testDeadline = LocalDateTime.of(LocalDate.of(2024, 10, 11), LocalTime.of(11, 59));
        testTask.setDeadline(testDeadline);
        assertEquals(testDeadline, testTask.getDeadline());
    }

    @Test
    void testIsDue() {
        LocalDateTime testDeadline = LocalDateTime.now().plusHours(48);
        testTask.setDeadline(testDeadline);
        assertTrue(testTask.isDue());
        LocalDateTime testDeadline1 = LocalDateTime.now().plusHours(47);
        testTask.setDeadline(testDeadline1);
        assertTrue(testTask.isDue());
        // LocalDateTime testDeadline2 = LocalDateTime.now().plusHours(49);
        // testTask.setDeadline(testDeadline2);
        // assertFalse(testTask.isDue());
    }

    @Test
    void testIsCompleted() {
        assertFalse(testTask.isCompleted());
        testTask.markAsDone();
        assertTrue(testTask.isCompleted());
    }
}
