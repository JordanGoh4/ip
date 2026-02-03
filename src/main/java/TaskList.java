package ip.src.main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a list of tasks and provides operations to manipulate the list.
 *
 * @author Jordan
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList backed by a copy of the given tasks.
     *
     * @param tasks Initial tasks to populate the list with.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index Zero-based index of task to remove.
     * @return The removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index Zero-based index of task to retrieve.
     * @return The task at that index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns an unmodifiable view of tasks for display.
     *
     * @return Unmodifiable list of tasks.
     */
    public List<Task> asUnmodifiableList() {
        return Collections.unmodifiableList(tasks);
    }
}

