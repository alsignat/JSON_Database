package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {

    private static final Database db = new Database();

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        String welcomeMessage = "Server started!";
        System.out.println(welcomeMessage);
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));) {
            while (true) {
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
                String request = input.readUTF();
                if (request.startsWith("exit")) {
                    output.writeUTF("OK");
                    server.close();
                    break;
                }
                try {
                    output.writeUTF(executeCommand(request));
                } catch (Exception e) {
                output.writeUTF("ERROR");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String executeCommand(String input) throws Exception {
        Gson gson = new Gson();
        JsonObject request = gson.fromJson(input);




        String[] command = input.split(" ");
        String method = command[0];
        String key = Integer.parseInt(command[1]);
        String value = String.join(" ", Arrays.copyOfRange(command, 2, command.length));

        String answer;
        switch (method) {
            case "set":
                answer = db.set(key, value);
                break;
            case "get":
                answer = db.get(key);
                break;
            case "delete":
                answer = db.delete(key);
                break;
            default:
                answer = "ERROR";
        };
        return answer;
    }

 */
}
