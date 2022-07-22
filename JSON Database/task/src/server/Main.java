package server;

import com.google.gson.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Main {

    private static final Database db = new Database();

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        String welcomeMessage = "Server started!";
        System.out.println(welcomeMessage);
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            while (true) {
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
                String request = input.readUTF();
                Gson gson = db.getGson();
                JsonObject jRequest = gson.fromJson(request, JsonObject.class);
                String type = jRequest.get("type").getAsString();
                if ("exit".equals(type)) {
                    output.writeUTF("{\"response\":\"OK\"}");
                    server.close();
                    break;
                }
                String key = Objects.requireNonNullElse(jRequest.get("key"), new JsonPrimitive("")).getAsString();
                String value = Objects.requireNonNullElse(jRequest.get("value"), new JsonPrimitive("")).getAsString();
                try {
                    output.writeUTF(executeCommand(type, key, value));
                } catch (Exception e) {
                output.writeUTF("ERROR WHILE TRYING TO EXECUTE COMMAND");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String executeCommand(String type, String key, String value) throws Exception {
        String answer;
        switch (type) {
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
                answer = "{\"response\":\"ERROR, COMMAND NOT RECOGNISED\"}";
        }
        return answer;
    }
}
