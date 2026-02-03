package ip.src.main.java;

import java.util.List;
import java.util.Scanner;

/**
 * Handles all interactions with the user (input and output).
 *
 * @author Jordan
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner in;

    /**
     * Constructs a Ui that reads from the given scanner.
     *
     * @param in Scanner to read user input from.
     */
    public Ui(Scanner in) {
        this.in = in;
    }

    /**
     * Prints the greeting message.
     *
     * @param name Chatbot name.
     */
    public void showGreeting(String name) {
        String greeting = " ___________________________\n"
                + "Hello! I'm " + name + "\n"
                + "What can I do for you?\n"
                + "___________________________\n";
        System.out.println(greeting);
    }

    /**
     * Reads the next user command line.
     *
     * @return The raw user input.
     */
    public String readCommand() {
        return in.nextLine();
    }

    /**
     * Prints the farewell message.
     */
    public void showFarewell() {
        String farewell = LINE + "\n"
                + "Woof. Hope to see you again soon!\n"
                + LINE + "\n";
        System.out.println(farewell);
    }

    /**
     * Shows an error message to the user.
     *
     * @param message Error message.
     */
    public void showError(String message) {
        System.out.println(LINE);
        System.out.println(" OOPS!!! " + message);
        System.out.println(LINE);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        List<Task> list = tasks.asUnmodifiableList();
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + list.get(i));
        }
        System.out.println(LINE);
    }

    /**
     * Shows a message after adding a task.
     *
     * @param task Added task.
     * @param totalTasks New total number of tasks.
     */
    public void showAdded(Task task, int totalTasks) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Shows a message after deleting a task.
     *
     * @param task Deleted task.
     * @param totalTasks New total number of tasks.
     */
    public void showDeleted(Task task, int totalTasks) {
        System.out.println(LINE);
        System.out.println("Woof! No more task!");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Shows a message after marking a task as done.
     *
     * @param task Marked task.
     */
    public void showMarked(Task task) {
        System.out.println(LINE);
        System.out.println("Woof! I have marked this task as done:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    /**
     * Shows a message after marking a task as not done.
     *
     * @param task Unmarked task.
     */
    public void showUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("Woof! I have marked this task as undone:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }
}

