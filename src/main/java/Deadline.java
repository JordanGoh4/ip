package ip.src.main.java;
import ip.src.main.java.Task;
import java.time.LocalDateTime;

/**
 * Represents a Deadline task, which is a task with a specific due date and time.
 * 
 * @author Jordan
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Constructs a new Deadline task with the given description and due date/time.
     * 
     * @param description The description of the deadline task
     * @param by The due date and time for the task
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Gets the due date and time of the deadline task.
     * 
     * @return The LocalDateTime representing when the task is due
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the Deadline task.
     * Format: "[D][status] description (by: dateTime)"
     * 
     * @return A string representation of the Deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
