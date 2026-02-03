package ip.src.main.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles loading tasks from a file and saving tasks back to a file.
 *
 * @author Jordan
 */
public class Storage {
    private final String filePath;
    private final DateTimeFormatter formatter;

    /**
     * Constructs a Storage with the given file path and date/time formatter.
     *
     * @param filePath File path of the task data file.
     * @param formatter Formatter for parsing/formatting date-time values.
     */
    public Storage(String filePath, DateTimeFormatter formatter) {
        this.filePath = filePath;
        this.formatter = formatter;
    }

    /**
     * Loads tasks from the data file.
     *
     * @return A TaskList containing tasks loaded from the file.
     * @throws IOException If reading the file fails.
     */
    public TaskList load() throws IOException {
        File f = new File(this.filePath);
        if (!f.exists()) {
            return new TaskList();
        }

        List<Task> tasks = new ArrayList<>();
        try (Scanner file = new Scanner(f)) {
            while (file.hasNext()) {
                String line = file.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                tasks.add(parseTask(line));
            }
        }
        return new TaskList(tasks);
    }

    /**
     * Saves tasks to the data file.
     *
     * @param taskList Task list to save.
     * @throws IOException If writing the file fails.
     */
    public void save(TaskList taskList) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList.asUnmodifiableList()) {
            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(serializeTask(task));
        }

        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write(sb.toString());
        }
    }

    private Task parseTask(String line) {
        String[] parts = line.split("\\|");
        String type = parts[0];
        String statusIcon = parts[1];
        String description = parts[2];
        int status = statusIcon.equals("X") ? 1 : 0;

        Task task;
        if (type.equals("ToDo")) {
            task = new ToDo(description);
        } else if (type.equals("Deadline")) {
            LocalDateTime by = LocalDateTime.parse(parts[3], formatter);
            task = new Deadline(description, by);
        } else if (type.equals("Event")) {
            LocalDateTime start = LocalDateTime.parse(parts[3], formatter);
            LocalDateTime end = LocalDateTime.parse(parts[4], formatter);
            task = new Event(start, end, description);
        } else {
            task = new Task(description);
        }

        task.setStatus(status);
        return task;
    }

    private String serializeTask(Task task) {
        if (task instanceof Event) {
            Event e = (Event) task;
            return "Event|" + task.getStatus() + "|" + task.getDescription() + "|"
                    + formatter.format(e.getStart()) + "|" + formatter.format(e.getEnd());
        }
        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "Deadline|" + task.getStatus() + "|" + task.getDescription() + "|"
                    + formatter.format(d.getBy());
        }
        if (task instanceof ToDo) {
            return "ToDo|" + task.getStatus() + "|" + task.getDescription();
        }
        return "Task|" + task.getStatus() + "|" + task.getDescription();
    }
}

