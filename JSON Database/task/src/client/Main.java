package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        RequestArgs cli = new RequestArgs();
        JCommander jcommand = JCommander.newBuilder()
                .addObject(cli)
                .build();
        jcommand.parse(args);
        Gson gson = new Gson();
        String request = gson.toJson(cli);
        String address = "127.0.0.1";
        int port = 23456;
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
        System.out.println("Client started!");
        System.out.println("Sent: " + request);
        output.writeUTF(request);
        String answer = input.readUTF();
        System.out.println("Received: " + answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}