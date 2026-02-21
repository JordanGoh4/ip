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
    private final Scanner in;
    private final PrintStream out;

    /**
     * Constructs a Ui that reads from the given scanner and prints to System.out.
     *
     * @param in Scanner to read user input from.
     */
    public Ui(Scanner in) {
        this.in = in;
        this.out = System.out;
    }

    /**
     * Constructs a Ui with custom input and output (e.g. for GUI to capture output).
     *
     * @param in  Scanner to read user input from (can be a dummy for GUI).
     * @param out PrintStream to write output to.
     */
    public Ui(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out != null ? out : System.out;
    }

    private void println(String s) {
        out.println(s);
    }

    /**
     * Prints the greeting message.
     *
     * @param name Chatbot name.
     */
    public void showGreeting(String name) {
        println(" ___________________________");
        println("Hello! I'm " + name);
        println("What can I do for you?");
        println("___________________________");
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
        println("Woof. Hope to see you again soon!");
    }

    /**
     * Shows an error message to the user.
     *
     * @param message Error message.
     */
    public void showError(String message) {
        println(" OOPS!!! " + message);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        println("Here are the tasks in your list:");
        List<Task> list = tasks.asUnmodifiableList();
        for (int i = 0; i < list.size(); i++) {
            println((i + 1) + "." + list.get(i));
        }
    }

    /**
     * Shows a message after adding a task.
     *
     * @param task Added task.
     * @param totalTasks New total number of tasks.
     */
    public void showAdded(Task task, int totalTasks) {
        println("Got it. I've added this task:");
        println("  " + task);
        println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Shows a message after deleting a task.
     *
     * @param task Deleted task.
     * @param totalTasks New total number of tasks.
     */
    public void showDeleted(Task task, int totalTasks) {
        println("Woof! No more task!");
        println("  " + task);
        println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Shows a message after marking a task as done.
     *
     * @param task Marked task.
     */
    public void showMarked(Task task) {
        println("Woof! I have marked this task as done:");
        println("  " + task);
    }

    /**
     * Shows a message after marking a task as not done.
     *
     * @param task Unmarked task.
     */
    public void showUnmarked(Task task) {
        println("Woof! I have marked this task as undone:");
        println("  " + task);
    }

    public void showFound(List<Task> found) {
        println("Here are the matching tasks in your list:");
        for (int i = 0; i < found.size(); i++) {
            println((i + 1) + "." + found.get(i));
        }
    }
}

