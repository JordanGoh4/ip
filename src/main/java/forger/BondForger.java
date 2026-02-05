package forger;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Main entry-point for the Bond Forger chatbot.
 *
 * @author Jordan
 */
public class BondForger extends Application {

    private static final String BOT_NAME = "Bond Forger";
    private static final String DATA_FILE_PATH = "forger/data.txt";
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static void main(String[] args) throws IOException {
        Ui ui = new Ui(new Scanner(System.in));
        Storage storage = new Storage(DATA_FILE_PATH, DATE_TIME_FORMAT);
        TaskList tasks = storage.load();

        ui.showGreeting(BOT_NAME);

        boolean isRunning = true;
        while (isRunning) {
            try {
                // Parse user command into their respective command type
                Parser.ParsedCommand command = Parser.parse(ui.readCommand(), DATE_TIME_FORMAT);
                isRunning = execute(command, tasks, ui);
            } catch (Bark e) {
                ui.showError(e.getMessage());
            }
        }

        storage.save(tasks);
        ui.showFarewell();
    }

    private static boolean execute(Parser.ParsedCommand command, TaskList tasks, Ui ui) throws Bark {
        switch (command.type) {
        case BYE:
            return false;
        case LIST:
            ui.showTaskList(tasks);
            return true;
        case MARK: {
            Task task = getTaskByIndex(command.index, tasks);
            task.setStatus(1);
            ui.showMarked(task);
            return true;
        }
        case UNMARK: {
            Task task = getTaskByIndex(command.index, tasks);
            task.setStatus(0);
            ui.showUnmarked(task);
            return true;
        }
        case DELETE: {
            Task removed = removeTaskByIndex(command.index, tasks);
            ui.showDeleted(removed, tasks.size());
            return true;
        }
        case TODO: {
            Task task = new ToDo(command.description);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
            return true;
        }
        case DEADLINE: {
            Task task = new Deadline(command.description, command.by);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
            return true;
        }
        case EVENT: {
            Task task = new Event(command.start, command.end, command.description);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
            return true;
        }
        case FIND: {
            List<Task> found = new ArrayList<>();
            for (Task task : tasks.asUnmodifiableList()) {
                if (task.getDescription().contains(command.description)) {
                    found.add(task);
                }
            }
            ui.showFound(found);
            return true;
        }
        default:
            throw new Bark("Bark Bark intruder alert!");
        }
    }

    private static Task getTaskByIndex(int index, TaskList tasks) throws Bark {
        validateIndex(index, tasks);
        return tasks.get(index);
    }

    private static Task removeTaskByIndex(int index, TaskList tasks) throws Bark {
        validateIndex(index, tasks);
        return tasks.remove(index);
    }

    private static void validateIndex(int index, TaskList tasks) throws Bark {
        if (index < 0 || index >= tasks.size()) {
            throw new Bark("That task number does not exist.");
        }
    }

    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!"); // Creating a new Label control
        Scene scene = new Scene(helloWorld); // Setting the scene to be our Label

        stage.setScene(scene); // Setting the stage to show our scene
        stage.show(); // Render the stage.
    }
}