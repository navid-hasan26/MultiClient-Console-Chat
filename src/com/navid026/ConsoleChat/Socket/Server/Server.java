package com.navid026.ConsoleChat.Socket.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    static Vector<ClientHandler> clientList = new Vector<>();
    static int clientID = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        Socket socket;
        while (true) {
            System.out.println("Waiting for Client...");
            socket = serverSocket.accept();
            System.out.println("Client connected");

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println("New Handler for this user created.");
            dataOutputStream.writeUTF("Server connected.");

            ClientHandler clientHandler = new ClientHandler(socket, "client " + clientID, dataInputStream, dataOutputStream);

            Thread thread = new Thread(clientHandler);

            clientList.add(clientHandler);

            thread.start();

            clientID++;
        }
    }
}
