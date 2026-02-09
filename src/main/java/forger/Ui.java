package forger;

import java.io.PrintStream;
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
    private final PrintStream out;  // Add this field

    /**
     * Constructs a Ui that reads from the given scanner and prints to System.out.
     *
     * @param in Scanner to read user input from.
     */
    public Ui(Scanner in) {
        this.in = in;
        this.out = System.out;  // Default to System.out
    }

    /**
     * Constructs a Ui with custom input and output streams.
     *
     * @param in Scanner to read user input from.
     * @param out PrintStream to write output to.
     */
    public Ui(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
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
        out.println(greeting);
    }

    /**
     * Reads the next user command line (CLI only).
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
        out.println(farewell);
    }

    /**
     * Shows an error message to the user.
     *
     * @param message Error message.
     */
    public void showError(String message) {
        out.println(LINE);
        out.println(" OOPS!!! " + message);
        out.println(LINE);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        out.println(LINE);
        out.println("Here are the tasks in your list:");
        List<Task> list = tasks.asUnmodifiableList();
        for (int i = 0; i < list.size(); i++) {
            out.println((i + 1) + "." + list.get(i));
        }
        out.println(LINE);
    }

    /**
     * Shows a message after adding a task.
     *
     * @param task Added task.
     * @param totalTasks New total number of tasks.
     */
    public void showAdded(Task task, int totalTasks) {
        out.println(LINE);
        out.println("Got it. I've added this task:");
        out.println("  " + task);
        out.println("Now you have " + totalTasks + " tasks in the list.");
        out.println(LINE);
    }

    /**
     * Shows a message after deleting a task.
     *
     * @param task Deleted task.
     * @param totalTasks New total number of tasks.
     */
    public void showDeleted(Task task, int totalTasks) {
        out.println(LINE);
        out.println("Woof! No more task!");
        out.println("  " + task);
        out.println("Now you have " + totalTasks + " tasks in the list.");
        out.println(LINE);
    }

    /**
     * Shows a message after marking a task as done.
     *
     * @param task Marked task.
     */
    public void showMarked(Task task) {
        out.println(LINE);
        out.println("Woof! I have marked this task as done:");
        out.println("  " + task);
        out.println(LINE);
    }

    /**
     * Shows a message after marking a task as not done.
     *
     * @param task Unmarked task.
     */
    public void showUnmarked(Task task) {
        out.println(LINE);
        out.println("Woof! I have marked this task as undone:");
        out.println("  " + task);
        out.println(LINE);
    }

    public void showFound(List<Task> found) {
        out.println(LINE);
        out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < found.size(); i++) {
            out.println((i + 1) + "." + found.get(i));
        }
        out.println(LINE);
    }
}