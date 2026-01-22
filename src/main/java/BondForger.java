import java.util.*;

public class BondForger {
    public static void main(String[] args) {
        String name = "Bond Forger";
        List<Task> library = new ArrayList<>();
        String greeting = " ___________________________\n"
                + "Hello! I'm " +  name + "\n"
                + "What can I do for you?\n"
                + "___________________________\n";
        System.out.println(greeting);
        Scanner input = new Scanner(System.in);
        boolean check = true;
        while (check){
            String user = input.nextLine();
            String[] parts = user.split(" ", 2); // Split into max 2 parts
            String command = parts[0];

            if (command.equals("bye")){
                check = false;
                continue;
            }

            if (command.equals("mark")) {
                int task_number = Integer.parseInt(parts[1]) - 1;
                Task v = library.get(task_number);
                System.out.println("____________________________________________________________");
                System.out.println("Woof! I have marked this task as done:");
                v.setStatus(true);
                System.out.println("  " + v);
                System.out.println("____________________________________________________________");
                continue;
            }

            if (command.equals("unmark")) {
                int task_number = Integer.parseInt(parts[1]) - 1;
                Task v = library.get(task_number);
                System.out.println("____________________________________________________________");
                System.out.println("Woof! I have marked this task as undone:");
                v.setStatus(false);
                System.out.println("  " + v);
                System.out.println("____________________________________________________________");
                continue;
            }

            if (command.equals("list")){
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int x = 0; x < library.size() ; x++){
                    System.out.println((x+1) + "." + library.get(x));
                }
                System.out.println("____________________________________________________________");
                continue;
            }

            if (command.equals("todo")) {
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
                Task t = new Deadline(description, by);
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
                String start = eventParts[1];
                String end = eventParts[2];
                Task t = new Event(start, end, description);
                library.add(t);
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + t);
                System.out.println("Now you have " + library.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
                continue;
            }
        }
        String farewell = "____________________________________________________________\n"
                + "Woof. Hope to see you again soon!\n"
                + "____________________________________________________________\n";
        System.out.println(farewell);
    }

    public static class Task{
        protected String description;
        protected boolean status;

        public Task(String description){
            this.description = description;
            this.status = false;
        }

        public void setStatus(boolean status){
            this.status = status;
        }

        public String getStatus(){
            return (this.status ? "X" : " ");
        }

        @Override
        public String toString() {
            return "[" + getStatus() + "] " + description;
        }
    }

    public static class Event extends Task{
        protected String start;
        protected String end;

        public Event(String start, String end, String description){
            super(description);
            this.end = end;
            this.start = start;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
        }
    }

    public static class Deadline extends Task {
        protected String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    public static class ToDo extends Task {
        public ToDo(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }
}