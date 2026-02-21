package forger;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Main entry-point for the Bond Forger chatbot.
 *
 * @author Jordan
 */
public class BondForger extends Application{

    // Package-visible so GUI logic (Bond) can reuse the same configuration.
    static final String BOT_NAME = "Bond Forger";
    static final String DATA_FILE_PATH = "C:\\Users\\jorda\\OneDrive\\Desktop\\2103\\ip\\src\\main\\java\\forger\\data.txt";
    static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Entry point for the text-based (terminal) version.
     */
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

    /**
     * Executes one parsed command and updates the task list.
     * Shared by both the CLI and JavaFX GUI.
     */
    static boolean execute(Parser.ParsedCommand command, TaskList tasks, Ui ui) throws Bark {
        switch (command.type) {
        case BYE:
            return false;
        case LIST:
            ui.showTaskList(tasks);
            return true;
        case MARK: {
            assert command.index >= 0;
            Task task = getTaskByIndex(command.index, tasks);
            task.setStatus(1);
            ui.showMarked(task);
            return true;
        }
        case UNMARK: {
            assert command.index >= 0;
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
            assert command.by != null : "DEADLINE must have a non-null 'by' time";
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
            assert false : "Unhandled command type: " + command.type;
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

//    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BondForger.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            stage.setScene(new Scene(ap));
            // Inject the chatbot logic used by the GUI.
            fxmlLoader.<MainWindow>getController().setDuke(new Bond());
            stage.setTitle(BOT_NAME);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}