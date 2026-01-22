import java.util.*;

public class BondForger {
    public static void main(String[] args) {
        String name = "Bond Forger";
        List<String> library = new ArrayList<>();
        String greeting = " ___________________________\n"
                + "Hello! I'm " +  name + "\n"
                + "What can I do for you?\n"
                + "___________________________\n";
        System.out.println(greeting);
        Scanner input = new Scanner(System.in);
        boolean check = true;
        while (check){
            String user = input.nextLine();
            if (user.equals("bye")){
                check = false;
            }else if (user.equals("list")){
                for (int x = 0; x < library.size() ; x++){
                    System.out.println(x+1 + ". " + library.get(x));
                }
            }else{
                System.out.println("Added: " + user);
                library.add(user);
//                System.out.println(user);
            }
        }
        String farewell = "_______________________________\n"
                + "Woof. Hope to see you again soon!\n"
                + " ___________________________\n";
        System.out.println(farewell);
    }
}
