package ui;

import javax.swing.*;
import model.EventLog;
import model.Task;
import model.Event;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Initializing the GUI components such as frames, panels, and menu bars.
 * Handling user interactions through buttons, menus, and input fields.
 * Persisting and loading tasks from a file using serialization.
 * Dynamically updatig the task list based on user actions.
 */
public class TaskManagerGUI extends JFrame {
    private List<Task> tasks = new ArrayList<>();
    private DefaultListModel<Task> taskListModel = new DefaultListModel<>();
    private JList<Task> taskList = new JList<>(taskListModel);
    private JTextField taskName = new JTextField(10);
    private JTextField taskTime = new JTextField(10);
    private JTextField taskDeadline = new JTextField(10);
    private JTextField filterTime = new JTextField(5);

    public TaskManagerGUI() {
        super("Task Manager");
        initializeFrame();
        initializeTaskList();
        initializeBottomPanel();
        initializeFilterPanel();
        initializeMenuBar();
        initializeWindowListener();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the frame of the main menu
    private void initializeFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setTitle("Task Manager");
    }

    // MODIFIES: this
    // EFFECTS: initializes the task list with single selection mode and adds it to
    // the center of the frame
    private void initializeTaskList() {
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes the bottom panel with task input fields and adds it to
    // the frame
    private void initializeBottomPanel() {
        JPanel bottomPanel = new JPanel(new GridLayout(3, 2));
        bottomPanel.add(new JLabel("Task Name:"));
        bottomPanel.add(taskName);
        bottomPanel.add(new JLabel("Time Required:"));
        bottomPanel.add(taskTime);
        bottomPanel.add(new JLabel("Deadline (yyyy-MM-dd HH:mm):"));
        bottomPanel.add(taskDeadline);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes the filter panel with filter input and button, and adds
    // it to the frame
    private void initializeFilterPanel() {
        JPanel filterPanel = new JPanel();
        JButton filterButton = new JButton("Filter by Time Required");
        filterButton.addActionListener(new Filter());
        filterPanel.add(new JLabel("Time Required >"));
        filterPanel.add(filterTime);
        filterPanel.add(filterButton);
        add(filterPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu bar with task management options and sets it to
    // the frame
    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        JMenuItem addTaskItem = new JMenuItem("Add Task");
        addTaskItem.addActionListener(new AddTask());
        menu.add(addTaskItem);

        JMenuItem viewTaskItem = new JMenuItem("View Task");
        viewTaskItem.addActionListener(new ViewTask());
        menu.add(viewTaskItem);

        JMenuItem removeTaskItem = new JMenuItem("Remove Task");
        removeTaskItem.addActionListener(new RemoveTask());
        menu.add(removeTaskItem);

        JMenuItem saveItem = new JMenuItem("Save Tasks");
        saveItem.addActionListener(new SaveTasks());
        menu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load Tasks");
        loadItem.addActionListener(new LoadTasksAction());
        menu.add(loadItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: adds a window listener to handle application closure events
    private void initializeWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Application is closing. Event log:");
                // Log the events before application closes
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.getTimestamp());
                    System.out.println(event.getDescription());
                }
                EventLog.getInstance().logEvent(new Event("Application closed."));
                System.exit(0);
            }
        });
    }

    private class AddTask implements ActionListener {
        // MODIFIES: tasks, taskListModel, taskName, taskTime, taskDeadline
        // EFFECTS: adds a new task to the list and clears the input fields; shows error
        // messages for invalid input
        public void actionPerformed(ActionEvent e) {
            try {
                int timeRequired = Integer.parseInt(taskTime.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime deadline = LocalDateTime.parse(taskDeadline.getText(), formatter);

                Task task = new Task(taskName.getText(), timeRequired, deadline);
                tasks.add(task);
                taskListModel.addElement(task);
                EventLog.getInstance().logEvent(new Event("Task added: " + task.getTaskName()));

                taskName.setText("");
                taskTime.setText("");
                taskDeadline.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TaskManagerGUI.this, "Please enter a valid time.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(TaskManagerGUI.this, "Invalid deadline format. Use yyyy-MM-dd HH:mm.");
            }
        }
    }

    private class ViewTask implements ActionListener {
        // EFFECTS: displays the details of the selected task; shows an error if no task
        // is selected
        public void actionPerformed(ActionEvent e) {
            Task selectedTask = taskList.getSelectedValue();
            if (selectedTask != null) {
                JOptionPane.showMessageDialog(TaskManagerGUI.this,
                        "Task: " + selectedTask.getTaskName()
                                + "\nTime Required: " + selectedTask.getTimeRequired()
                                + "\nDeadline: "
                                + selectedTask.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        "Task Details",
                        JOptionPane.INFORMATION_MESSAGE);
                EventLog.getInstance().logEvent(new Event("Task viewed: " + selectedTask.getTaskName()));
            } else {
                JOptionPane.showMessageDialog(TaskManagerGUI.this, "Please select a task to view.");
            }
        }
    }

    private class RemoveTask implements ActionListener {
        // MODIFIES: tasks, taskListModel
        // EFFECTS: removes the selected task from the list; shows an error if no task
        // is selected
        public void actionPerformed(ActionEvent e) {
            Task selectedTask = taskList.getSelectedValue();
            if (selectedTask != null) {
                tasks.remove(selectedTask);
                taskListModel.removeElement(selectedTask);
                EventLog.getInstance().logEvent(new Event("Task removed: " + selectedTask.getTaskName()));
            } else {
                JOptionPane.showMessageDialog(TaskManagerGUI.this, "Please select a task to remove.");
            }
        }
    }

    private class Filter implements ActionListener {
        // MODIFIES: taskListModel
        // EFFECTS: filters tasks based on time required and updates the task list
        // model; shows an error for invalid input
        public void actionPerformed(ActionEvent e) {
            try {
                int filterTimeMinutes = Integer.parseInt(filterTime.getText());
                taskListModel.clear();
                tasks.stream()
                        .filter(task -> task.getTimeRequired() > filterTimeMinutes)
                        .forEach(taskListModel::addElement);
                EventLog.getInstance().logEvent(new Event("Tasks filtered: Time required > " 
                        + filterTimeMinutes));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TaskManagerGUI.this, "Please enter a valid time.");
            }
        }
    }

    private class SaveTasks implements ActionListener {
        // EFFECTS: saves the current list of tasks to a file; shows an error if saving
        // fails
        public void actionPerformed(ActionEvent e) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tasks.dat"))) {
                oos.writeObject(tasks);
                JOptionPane.showMessageDialog(TaskManagerGUI.this, "Tasks saved successfully.");
                EventLog.getInstance().logEvent(new Event("Data saved: All tasks saved to tasks.dat."));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(TaskManagerGUI.this, "Error saving tasks.");
            }
        }
    }

    private class LoadTasksAction implements ActionListener {
        // MODIFIES: tasks, taskListModel
        // EFFECTS: loads tasks from a file and updates the task list model; shows an
        // error if loading fails
        public void actionPerformed(ActionEvent e) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tasks.dat"))) {
                tasks = (List<Task>) ois.readObject();
                taskListModel.clear();
                tasks.forEach(taskListModel::addElement);
                JOptionPane.showMessageDialog(TaskManagerGUI.this, "Tasks loaded successfully.");
                EventLog.getInstance().logEvent(new Event("Data loaded: Tasks loaded from tasks.dat."));
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(TaskManagerGUI.this, "Error loading tasks.");
            }
        }
    }

    public static void main(String[] args) {
        new TaskManagerGUI();
    }
}