package ip.src.main.java;
import java.time.LocalDateTime;

public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    public Event(LocalDateTime start, LocalDateTime end, String description){
        super(description);
        this.end = end;
        this.start = start;
    }

    public LocalDateTime getStart(){
        return this.start;
    }

    public LocalDateTime getEnd(){
        return this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}