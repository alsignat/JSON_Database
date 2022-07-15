package server;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final Database db = new Database(100);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }
            try {
                executeCommand(input);
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }
    }

    public static void executeCommand(String input) throws Exception {
        String[] command = input.split(" ");
        String method = command[0];
        int index = Integer.parseInt(command[1]);
        String value = String.join(" ", Arrays.copyOfRange(command, 2, command.length));

        Method runMethod;
        if (command.length >= 3) {
            runMethod = db.getClass().getMethod(method, int.class, String.class);
            runMethod.invoke(db, index - 1, value);
        } else if (command.length == 2) {
            runMethod = db.getClass().getMethod(method, int.class);
            runMethod.invoke(db, index - 1);
        }
    }
}
