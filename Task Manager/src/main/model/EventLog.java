package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a log of task events.
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the tasks and that the tasks have global access
 * to the single instance of the EventLog.
 */
public class EventLog implements Iterable<Event> {
    private static final EventLog instance = new EventLog();
    private final List<Event> events;

    // Constrct an Eventlog of Tasks.
    private EventLog() {
        events = new ArrayList<>();
    }

    // EFFECTS: create and returns as EventLog instance.
    // (EventLog constructor is private)
    public static EventLog getInstance() {
        return instance;
    }

    // EFFECTS: adds a task event to EventLog list of task events.
    public void logEvent(Event event) {
        events.add(event);
    }

    // MODIFIES: events.
    // EFFECTS: return an Array of all log task events in correct sequance.
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }

    // MODIFIES: events.
    // EFFECTS: delet all task events from logEvent events. and print
    // a "Event log cleared." message.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }
}