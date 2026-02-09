package forger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Chatbot logic used by the JavaFX GUI.
 * Reuses the same Parser + execute + Storage/TaskList code as the CLI.
 */
public class Bond {

    private final Storage storage;
    private final TaskList tasks;

    /**
     * Creates a Bond chatbot using the same data file and date format as the CLI.
     */
    public Bond() {
        this.storage = new Storage(BondForger.DATA_FILE_PATH, BondForger.DATE_TIME_FORMAT);
        TaskList loaded;
        try {
            loaded = storage.load();
        } catch (Exception e) {
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    /**
     * Generates a response for the user's chat message.
     * Captures the same text that would normally be printed to the terminal.
     *
     * @param input Raw user input from the GUI text field.
     * @return Text to display in the GUI dialog.
     */
    public String getResponse(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }

        // Create a ByteArrayOutputStream to capture output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // Create a dummy scanner (not used for GUI)
        Scanner dummyScanner = new Scanner("");

        // Create Ui with custom output stream
        Ui ui = new Ui(dummyScanner, ps);

        try {
            Parser.ParsedCommand parsed =
                    Parser.parse(input.trim(), BondForger.DATE_TIME_FORMAT);
            boolean keepRunning = BondForger.execute(parsed, tasks, ui);
            if (!keepRunning) {
                storage.save(tasks);
                ui.showFarewell();
            }
        } catch (Bark e) {
            ui.showError(e.getMessage());
        } catch (Exception e) {
            return "OOPS!!! " + e.getMessage();
        }

        // Convert captured output to string
        return baos.toString();
    }
}