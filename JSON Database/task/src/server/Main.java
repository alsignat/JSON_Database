package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final Database db = new Database(1000);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        String welcomeMessage = "Server started!";
        System.out.println(welcomeMessage);
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
             Socket socket = server.accept();
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output  = new DataOutputStream(socket.getOutputStream());) {
            output.writeUTF(welcomeMessage);
            String request = input.readUTF();
            try {
                executeCommand(request);
            } catch (Exception e) {
                System.out.println("ERROR");
            }
            // System.out.println("Received: " + request);
            String[] requestedData = request.split(" ");
            output.writeUTF(serverResponse);
            System.out.println("Sent: " + serverResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        while (true) {
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }

        }
         */
    }

    public static String executeCommand(String input) throws Exception {
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
