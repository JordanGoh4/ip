package forger;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

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

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image userImage = new Image(this.getClass().
            getResourceAsStream("../images/DaUser.png"));
    private Bond bond = new Bond();

    @Override
    public void start(Stage stage) {
        //Setting up required components

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");


        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        stage.setTitle("BondForger");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        //Handling user input

        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        //More code to be added here later
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        String userText = userInput.getText();
        String dukeText = bond.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getDukeDialog(dukeText, userImage)
        );
        userInput.clear();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Bond heard: " + input;
    }
}