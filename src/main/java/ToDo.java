package ip.src.main.java;

/**
 * Represents a ToDo task, which is a simple task without any date or time constraints.
 * 
 * @author Jordan
 */
public class ToDo extends Task {
    /**
     * Constructs a new ToDo task with the given description.
     * 
     * @param description The description of the ToDo task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the ToDo task.
     * Format: "[T][status] description"
     * 
     * @return A string representation of the ToDo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}