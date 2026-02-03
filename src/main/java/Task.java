package ip.src.main.java;

/**
 * Represents a task with a description and completion status.
 * This is the base class for different types of tasks (ToDo, Deadline, Event).
 * 
 * @author Jordan
 */
public class Task{
    protected String description;
    protected int status;

    /**
     * Constructs a new Task with the given description.
     * The task is initialized as not completed (status = 0).
     * 
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.status = 0;
    }

    /**
     * Sets the completion status of the task.
     * 
     * @param status The status to set (1 for completed, 0 for not completed)
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the completion status as a string representation.
     * 
     * @return "X" if the task is completed, " " (space) if not completed
     */
    public String getStatus() {
        return (this.status == 1 ? "X" : " ");
    }

    /**
     * Gets the description of the task.
     * 
     * @return The task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of the task.
     * Format: "[status] description"
     * 
     * @return A string representation of the task
     */
    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}
