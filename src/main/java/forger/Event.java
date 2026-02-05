package forger;
import java.time.LocalDateTime;

/**
 * Represents an Event task, which is a task that occurs during a specific time period.
 * 
 * @author Jordan
 */
public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Constructs a new Event task with the given description, start time, and end time.
     * 
     * @param start The start date and time of the event
     * @param end The end date and time of the event
     * @param description The description of the event task
     */
    public Event(LocalDateTime start, LocalDateTime end, String description) {
        super(description);
        this.end = end;
        this.start = start;
    }

    /**
     * Gets the start date and time of the event.
     * 
     * @return The LocalDateTime representing when the event starts
     */
    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Gets the end date and time of the event.
     * 
     * @return The LocalDateTime representing when the event ends
     */
    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Returns a string representation of the Event task.
     * Format: "[E][status] description (from: startDateTime to: endDateTime)"
     * 
     * @return A string representation of the Event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}