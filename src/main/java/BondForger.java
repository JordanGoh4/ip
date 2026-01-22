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
            String[] parts = user.split(" ");
            String command = parts[0];
            if (command.equals("mark")) {
                int task_number = Integer.parseInt(parts[1]) - 1;
                Task v = library.get(task_number);
                System.out.println("Woof! I have marked this task as done: \n");
                v.setStatus(true);
                System.out.println("[" + v.getStatus() + "] " + v.description);
                continue;
            }

            if (command.equals("unmark")) {
                int task_number = Integer.parseInt(parts[1]) - 1;
                Task v = library.get(task_number);
                System.out.println("Woof! I have marked this task as undone: \n");
                v.setStatus(false);
                System.out.println("[" + v.getStatus() + "] " + v.description);
                continue;
            }

            Task t = new Task(user);
            if (command.equals("bye")){
                check = false;
            }else if (command.equals("list")){
                System.out.println("Here are the tasks in your list: \n");
                for (int x = 0; x < library.size() ; x++){
                    System.out.println(x+1 + ".[" + library.get(x).getStatus() + "] " + library.get(x).description);
                }
            }else{
                System.out.println("Added: " + user);
                library.add(t);
            }
        }
        String farewell = "_______________________________\n"
                + "Woof. Hope to see you again soon!\n"
                + "___________________________\n";
        System.out.println(farewell);
    }
}

class Task{
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
}