package forger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Checks for schedule clashes between timed tasks (Deadline and Event).
 * Two items clash if they occupy the same time (deadline at a moment, or event overlapping another).
 */
public final class ScheduleChecker {

    private ScheduleChecker() {
    }

    /**
     * Returns existing tasks that clash in schedule with the new task.
     * ToDo has no schedule, so it never clashes. Deadline and Event are checked against existing deadlines and events.
     *
     * @param existingTasks Current task list (will not be modified).
     * @param newTask       The task to add (Deadline or Event).
     * @return List of tasks that clash with {@code newTask}; empty if none.
     */
    public static List<Task> getClashingTasks(TaskList existingTasks, Task newTask) {
        List<Task> clashing = new ArrayList<>();
        if (newTask instanceof ToDo) {
            return clashing;
        }

        for (Task existing : existingTasks.asUnmodifiableList()) {
            if (existing instanceof ToDo) {
                continue;
            }
            if (clashes(newTask, existing)) {
                clashing.add(existing);
            }
        }
        return clashing;
    }

    /**
     * Returns true if the two tasks have overlapping schedules.
     */
    private static boolean clashes(Task a, Task b) {
        if (a instanceof Deadline && b instanceof Deadline) {
            return ((Deadline) a).getBy().equals(((Deadline) b).getBy());
        }
        if (a instanceof Deadline && b instanceof Event) {
            return eventContains(((Event) b).getStart(), ((Event) b).getEnd(), ((Deadline) a).getBy());
        }
        if (a instanceof Event && b instanceof Deadline) {
            return eventContains(((Event) a).getStart(), ((Event) a).getEnd(), ((Deadline) b).getBy());
        }
        if (a instanceof Event && b instanceof Event) {
            return rangesOverlap(
                    ((Event) a).getStart(), ((Event) a).getEnd(),
                    ((Event) b).getStart(), ((Event) b).getEnd()
            );
        }
        return false;
    }

    private static boolean eventContains(LocalDateTime start, LocalDateTime end, LocalDateTime point) {
        return !point.isBefore(start) && !point.isAfter(end);
    }

    private static boolean rangesOverlap(LocalDateTime start1, LocalDateTime end1,
                                         LocalDateTime start2, LocalDateTime end2) {
        return !end1.isBefore(start2) && !end2.isBefore(start1);
    }
}
