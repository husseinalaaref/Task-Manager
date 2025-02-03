package ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import model.Task;
import model.Tasks;
import persistence.JsonReader;
import persistence.JsonWriter;

public class TaskManagerUi {
    private static final String JSON_STORE = "./data/Tasks.json";
    private static Tasks tasks;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static Scanner scanner = new Scanner(System.in);

    // runs the TaskManagerUi.
    public TaskManagerUi() throws FileNotFoundException {
        runTasks();
    }

    // MODIFIES: this.
    // EFFECTS: displays the task manager menu and processes user input until the
    // user exits.
    private void runTasks() {
        System.out.println("\nWelcome to Tasks Manager!");
        boolean keepGoing = true;
        int command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextInt();

            if (command == 11) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Goodbye!");
    }

    // MODIFIES: this.
    // EFFECTS: initializes input, jsonWriter, jsonReader, and tasks, and adds
    // default tasks.
    private void init() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        tasks = new Tasks();
        Task cpsc = new Task("CPSC 210", 120, LocalDateTime.of(2024, 10, 11, 11, 59));
        Task math = new Task("MATH 302", 30, LocalDateTime.of(2024, 10, 11, 11, 59));
        tasks.addTask(cpsc);
        tasks.addTask(math);
    }

    // MODIFIES: this.
    // EFFECTS: displays the menu options to the user.
    private void displayMenu() {
        System.out.println("___ The Task Manager ___");
        System.out.println("1-> Add a new task");
        System.out.println("2-> Get tasks with time required more than X minutes:");
        System.out.println("3-> View current tasks");
        System.out.println("4-> Remove a task");
        System.out.println("5-> Mark a task as done");
        System.out.println("6-> Show Total time required for all tasks");
        System.out.println("7-> Show to-do Tasks");
        System.out.println("8-> Notify for Upcoming Deadlines");
        System.out.println("9-> Save tasks");
        System.out.println("10-> Load tasks");
        System.out.println("11-> Exit");
        System.out.print("Please select an option: ");
    }

    // REQUIRES: numerical input (whole numbers from interval [1,10])
    // EFFECTS: processes the user's command.
    @SuppressWarnings("methodlength")
    private void processCommand(int command) {
        switch (command) {
            case 1:
                addTask();
                break;
            case 2:
                getTasksRequiredTimeMoreThan();
                break;
            case 3:
                viewTasks();
                break;
            case 4:
                removeTask();
                break;
            case 5:
                markTaskAsDone();
                break;
            case 6:
                showTotalTimeRequired();
                break;
            case 7:
                showTasksToDo();
                break;
            case 8:
                notifyUserOfDeadlines();
                break;
            case 9:
                saveTasks();
                break;
            case 10:
                loadTasks();
                break;
            default:
                System.out.println("Invalid option. Please try a new entry.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new task with a name, time required, and deadline to the
    // tasks list.
    private static void addTask() {
        List<Task> tasksToAdd = tasks.viewTasks();

        System.out.print("Enter task's name: ");
        String taskName = scanner.nextLine();
        System.out.print("Enter time required to complete task (in minutes): ");
        int timeRequired = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter task's deadline (YYYY-MM-DD HH:MM): ");
        String deadlineInput = scanner.nextLine();
        String deadlineFormat = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

        if (deadlineInput.matches(deadlineFormat)) {
            LocalDateTime deadline = LocalDateTime.parse(deadlineInput.replace(" ", "T"));
            Task task = new Task(taskName, timeRequired, deadline);
            tasksToAdd.add(task);
            System.out.println("Task added successfully.");
        } else {
            System.out.println("You entered an invalid date or time!");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the list of all tasks.
    private static void viewTasks() {
        List<Task> tasksToView = tasks.viewTasks();

        if (tasksToView.isEmpty()) {
            System.out.println("There are no tasks to view");
        } else {
            for (Task task : tasksToView) {
                System.out.printf("Task name: %s, Task time required: %d, Task's deadline: %s%n",
                        task.getTaskName(), task.getTimeRequired(), task.getDeadline().toString().replace("T", " "));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the task with the provided name from the list of tasks.
    private static void removeTask() {
        List<Task> tasksToRemove = tasks.viewTasks();

        System.out.print("Enter task's name you want to remove: ");
        String taskName = scanner.next();

        if (tasksToRemove.isEmpty()) {
            System.out.println("There are no tasks to remove!");
            return;
        }
        boolean isThere = false;
        for (int i = 0; i < tasksToRemove.size(); i++) {
            Task task = tasksToRemove.get(i);
            if (task.getTaskName().equals(taskName)) {
                tasksToRemove.remove(i);
                System.out.println("The task with the following name was successfully removed: " + taskName);
                isThere = true;
                break;
            }
        }

        if (!isThere) {
            System.out.println("The task with the following name does NOT exist: " + taskName);
        }
    }

    // MODIFIES: this
    // EFFECTS: marks the task as done by updating its status to true.
    private static void markTaskAsDone() {
        System.out.print("Enter task's name you want to mark as done: ");
        String taskEntered = scanner.next();
        List<Task> tasksToMarkDone = tasks.viewTasks();

        if (tasksToMarkDone.isEmpty()) {
            System.out.println("There are no tasks to mark as done!");
            return;
        }

        boolean isThere = false;
        for (int i = 0; i < tasksToMarkDone.size(); i++) {
            Task task = tasksToMarkDone.get(i);
            if (task.getTaskName().equals(taskEntered)) {
                tasksToMarkDone.get(i).markAsDone();
                System.out.println("Task with the following name was marked as done: " + taskEntered);
                isThere = true;
                break;
            }
        }

        if (!isThere) {
            System.out.println("Task with the following name does NOT exist: " + taskEntered);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays all incomplete tasks.
    private static void showTasksToDo() {
        List<Task> tasksList = tasks.viewTasks();

        if (tasksList.isEmpty()) {
            System.out.println("There are no tasks to show as to-do tasks!");
            return;
        }

        boolean isThere = false;
        for (int i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);

            if (!task.isCompleted()) {
                System.out.println("The following tasks are incomplete: ");
                System.out.printf("Task name: %s, Task time required: %d, Task's deadline: %s%n",
                        task.getTaskName(), task.getTimeRequired(), task.getDeadline().toString().replace("T", " "));
                isThere = true;
            }
        }
        if (!isThere) {
            System.out.println("All tasks are completed. Well done!!");
        }
    }

    // MODIFIES: this
    // EFFECTS: calculates and displays the total time required to complete all
    // incomplete tasks.
    private static void showTotalTimeRequired() {
        List<Task> tasksList = tasks.viewTasks();

        if (tasksList.isEmpty()) {
            System.out.println("There are no tasks to work on!");
            return;
        }

        int totalTimeRequired = 0;
        for (int i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);
            if (!task.isCompleted()) {
                totalTimeRequired += task.getTimeRequired();
            }
        }
        System.out.println(
                "The total time required to complete all incomplete tasks is: " + totalTimeRequired + " minutes");
    }

    // MODIFIES: this
    // EFFECTS: notifies the user of upcoming task deadlines.
    private static void notifyUserOfDeadlines() {
        tasks.notifyUserOfDeadline();
    }

    // MODIFIES: this
    // EFFECTS: saves tasks to a JSON file.
    private static void saveTasks() {
        try {
            JsonWriter writer = new JsonWriter("./data/tasks.json");
            writer.write(tasks);
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save tasks: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads tasks from a JSON file.
    private static void loadTasks() {
        try {
            JsonReader reader = new JsonReader("./data/tasks.json");
            tasks = reader.read();
            System.out.println("Tasks loaded successfully.");
        } catch (IOException e) {
            System.out.println("Failed to load tasks: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: display tasks that require more time than the specified minimum
    // time.
    private static void getTasksRequiredTimeMoreThan() {

        System.out.print("Specify a minumum time required for tasks (in minutes): ");
        int timeAtLeast = scanner.nextInt();
        scanner.nextLine();

        List<Task> tasksToFilter = tasks.viewTasks();
        boolean taskFound = false;

        for (Task task : tasksToFilter) {
            if (task.getTimeRequired() >= timeAtLeast) {
                System.out.println("The following tasks have time required to complete greater than or equal to "
                        + timeAtLeast + "\r\n");
                System.out.printf("Task name: %s, Time required: %d, Deadline: %s%n",
                        task.getTaskName(), task.getTimeRequired(), task.getDeadline().toString().replace("T", " "));
                taskFound = true;
            }
        }

        if (!taskFound) {
            System.out.println("No tasks require greater than or equal to " + timeAtLeast + " minutes. to complete");
        }
    }
}
