package ip.src.main.java;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;


// text file is the database, load database into the array first and only overwrite when it is done.
public class BondForger {
    public static void main(String[] args) throws IOException {
        String name = "Bond Forger";
        String filePath = "ip/src/main/java/data.txt";
        File f = new File(filePath);
        Scanner file = new Scanner(f);
        List<Task> library = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        // Can create a function here
        while (file.hasNext()){
            String[] array = file.nextLine().split("\\|");
            if (array[0].equals("ToDo")){
                String description = array[2];
                int status = 0;
                if (array[1].equals("X")){
                    status = 1;
                }
                Task t = new ToDo(description);
                t.setStatus(status);
                library.add(t);
            }
            if (array[0].equals("Deadline")){
                String description = array[2];
                LocalDateTime date = LocalDateTime.parse(array[3], format);
                int status = 0;
                if (array[1].equals("X")){
                    status = 1;
                }
                Task t = new Deadline(description,date);
                t.setStatus(status);
                library.add(t);
            }
            if (array[0].equals("Event")){
                String description = array[2];
                int status = 0;
                LocalDateTime start = LocalDateTime.parse(array[3], format);
                LocalDateTime end = LocalDateTime.parse(array[4], format);
                if (array[1].equals("X")){
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
        boolean check = true;
        while (check) {
            try{
            String user = input.nextLine();
            String[] parts = user.split(" ", 2);
            String command = parts[0];

            if (command.equals("bye")) {
                check = false;
                continue;
            }

            if (command.equals("mark")) {
                int task_number = Integer.parseInt(parts[1]) - 1;
                if (task_number < 0 || task_number >= library.size()) {
                    throw new Bark("That task number does not exist.");
                }
                Task v = library.get(task_number);
                System.out.println("____________________________________________________________");
                System.out.println("Woof! I have marked this task as done:");
                v.setStatus(1);
                System.out.println("  " + v);
                System.out.println("____________________________________________________________");
                continue;
            }

            if (command.equals("unmark")) {
                int task_number = Integer.parseInt(parts[1]) - 1;
                if (task_number < 0 || task_number >= library.size()) {
                    throw new Bark("That task number does not exist.");
                }
                Task v = library.get(task_number);
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
                int task_number = Integer.parseInt(parts[1]) - 1;
                if (task_number < 0 || task_number >= library.size()) {
                    throw new Bark("That task number does not exist.");
                }
                Task removed = library.remove(task_number);
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
                for (int x = 0; x < library.size(); x++) {
                    System.out.println((x + 1) + "." + library.get(x));
                }
                System.out.println("____________________________________________________________");
                continue;
            }

            if (command.equals("todo")) {
                if (parts[1].trim().isEmpty()){
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
        for (Task x : library){
            if (x instanceof Event){
                String newTask = "Event|" + x.getStatus() + "|" + x.getDescription() + "|" +
                        ((Event) x).getStart() + "|" + ((Event) x).getEnd();
                fw.write("\n" + newTask);
                fw.flush();
            }
            if (x instanceof Deadline){
                String newTask = "Deadline|" + x.getStatus() + "|" + x.getDescription() + "|" + ((Deadline) x).getBy();
                fw.write("\n" + newTask);
                fw.flush();
            }
            if (x instanceof ToDo){
                String newTask = "ToDo|" + x.getStatus() + "|" + x.getDescription();
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