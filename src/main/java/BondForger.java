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
 * <h1>Bond Forger Chatbot</h1>
 * <p>
 * Main class to run Bond Forger chatbot. This chatbot allows users to manage tasks
 * including ToDo items, Deadline tasks, and Event tasks. Tasks are persisted to
 * a data file and loaded when the program starts.
 * </p>
 * <p>
 * Supported commands:
 * <ul>
 *   <li>list - Display all tasks</li>
 *   <li>todo [description] - Add a new ToDo task</li>
 *   <li>deadline [description] /by [date time] - Add a new Deadline task</li>
 *   <li>event [description] /from [date time] /to [date time] - Add a new Event task</li>
 *   <li>mark [number] - Mark a task as completed</li>
 *   <li>unmark [number] - Mark a task as not completed</li>
 *   <li>delete [number] - Delete a task</li>
 *   <li>bye - Exit the program</li>
 * </ul>
 * </p>
 * <p>
 * Date format: d/M/yyyy HHmm (e.g., "3/2/2026 1430" for February 3, 2026 at 2:30 PM)
 * </p>
 * 
 * @author Jordan
 */
public class BondForger {
    /**
     * Main method that runs the Bond Forger chatbot.
     * Loads tasks from the data file, processes user commands interactively,
     * and saves tasks back to the data file when the program exits.
     * 
     * @param args Command line arguments (not used)
     * @throws IOException If there is an error reading from or writing to the data file
     */
    public static void main(String[] args) throws IOException {
        String name = "Bond Forger";
        String filePath = "../src/main/java/data.txt";

        File f = new File(filePath);
        Scanner file = new Scanner(f);
        List<Task> library = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

        while (file.hasNext()) {
            String[] array = file.nextLine().split("\\|");
            if (array[0].equals("ToDo")) {
                String description = array[2];
                int status = 0;
                if (array[1].equals("X")) {
                    status = 1;
                }
                Task t = new ToDo(description);
                t.setStatus(status);
                library.add(t);
            } else if (array[0].equals("Deadline")) {
                String description = array[2];
                LocalDateTime date = LocalDateTime.parse(array[3], format);
                int status = 0;
                if (array[1].equals("X")) {
                    status = 1;
                }
                Task t = new Deadline(description, date);
                t.setStatus(status);
                library.add(t);
            } else if (array[0].equals("Event")) {
                String description = array[2];
                int status = 0;
                LocalDateTime start = LocalDateTime.parse(array[3], format);
                LocalDateTime end = LocalDateTime.parse(array[4], format);
                if (array[1].equals("X")) {
                    status = 1;
                }
                Task t = new Event(start, end, description);
                t.setStatus(status);
                library.add(t);
            }
        }
        String greeting = " ___________________________\n"
                + "Hello! I'm " + name + "\n"
                + "What can I do for you?\n"
                + "___________________________\n";
        System.out.println(greeting);
        Scanner input = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            try {
                String userInput = input.nextLine();
                String[] parts = userInput.split(" ", 2);
                String command = parts[0];

                if (command.equals("bye")) {
                    isRunning = false;
                    continue;
                }

                if (command.equals("mark")) {
                    int taskNumber = Integer.parseInt(parts[1]) - 1;
                    if (taskNumber < 0 || taskNumber >= library.size()) {
                        throw new Bark("That task number does not exist.");
                    }
                    Task v = library.get(taskNumber);
                    System.out.println("____________________________________________________________");
                    System.out.println("Woof! I have marked this task as done:");
                    v.setStatus(1);
                    System.out.println("  " + v);
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (command.equals("unmark")) {
                    int taskNumber = Integer.parseInt(parts[1]) - 1;
                    if (taskNumber < 0 || taskNumber >= library.size()) {
                        throw new Bark("That task number does not exist.");
                    }
                    Task v = library.get(taskNumber);
                    System.out.println("____________________________________________________________");
                    System.out.println("Woof! I have marked this task as undone:");
                    v.setStatus(0);
                    System.out.println("  " + v);
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (command.equals("delete")) {
                    if (parts.length < 2) {
                        throw new Bark("Please specify which task to delete.");
                    }
                    int taskNumber = Integer.parseInt(parts[1]) - 1;
                    if (taskNumber < 0 || taskNumber >= library.size()) {
                        throw new Bark("That task number does not exist.");
                    }
                    Task removed = library.remove(taskNumber);
                    System.out.println("____________________________________________________________");
                    System.out.println("Woof! No more task!");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + library.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (command.equals("list")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < library.size(); i++) {
                        System.out.println((i + 1) + "." + library.get(i));
                    }
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (command.equals("todo")) {
                    if (parts[1].trim().isEmpty()) {
                        throw new Bark("Borf! No empty.");
                    }
                    String description = parts[1];
                    Task t = new ToDo(description);
                    library.add(t);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + t);
                    System.out.println("Now you have " + library.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (command.equals("deadline")) {
                    String[] deadlineParts = parts[1].split(" /by ", 2);
                    String description = deadlineParts[0];
                    String by = deadlineParts[1];
                    LocalDateTime time = LocalDateTime.parse(by, format);
                    Task t = new Deadline(description, time);
                    library.add(t);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + t);
                    System.out.println("Now you have " + library.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (command.equals("event")) {
                    String[] eventParts = parts[1].split(" /from | /to ");
                    String description = eventParts[0];
                    LocalDateTime start = LocalDateTime.parse(eventParts[1], format);
                    LocalDateTime end = LocalDateTime.parse(eventParts[2], format);
                    Task t = new Event(start, end, description);
                    library.add(t);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + t);
                    System.out.println("Now you have " + library.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    continue;
                }

                throw new Bark("Bark Bark intruder alert!");
            } catch (Bark e) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

        FileWriter fw = new FileWriter(filePath);
        for (Task task : library) {
            if (task instanceof Event) {
                String newTask = "Event|" + task.getStatus() + "|" + task.getDescription() + "|"
                        + ((Event) task).getStart() + "|" + ((Event) task).getEnd();
                fw.write("\n" + newTask);
                fw.flush();
            }
            if (task instanceof Deadline) {
                String newTask = "Deadline|" + task.getStatus() + "|" + task.getDescription() + "|"
                        + ((Deadline) task).getBy();
                fw.write("\n" + newTask);
                fw.flush();
            }
            if (task instanceof ToDo) {
                String newTask = "ToDo|" + task.getStatus() + "|" + task.getDescription();
                fw.write("\n" + newTask);
                fw.flush();
            }
        }
        fw.close();
        String farewell = "____________________________________________________________\n"
                + "Woof. Hope to see you again soon!\n"
                + "____________________________________________________________\n";
        System.out.println(farewell);
    }
}