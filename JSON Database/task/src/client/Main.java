package client;

import com.beust.jcommander.JCommander;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        MainArgs cli = new MainArgs();
        JCommander jcommand = JCommander.newBuilder()
                .addObject(cli)
                .build();
        jcommand.parse(args);

        String address = "127.0.0.1";
        int port = 23456;
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
        String greeting = input.readUTF();
        System.out.println(greeting);
        String request = cli.getCLI();
        System.out.println("Sent: " + request);
        output.writeUTF(request);
        String answer = input.readUTF();
        System.out.println("Received: " + answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}