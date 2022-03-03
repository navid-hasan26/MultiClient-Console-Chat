package com.navid026.ConsoleChat.Socket.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    Scanner scanner = new Scanner(System.in);
    Socket socket;
    boolean logInStatus;
    private String name;

    public ClientHandler(Socket socket, String name, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.name = name;
        this.dataInputStream = dis;
        this.dataOutputStream = dos;
        this.logInStatus = true;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {
                received = dataInputStream.readUTF();
                System.out.println(received);
                if (received.equals("exit")) {
                    this.logInStatus = false;
                    this.socket.close();
                    break;
                }
                StringTokenizer st = new StringTokenizer(received, ">");
                String MsgToSend = st.nextToken();
                String recipient = st.nextToken();

                for (ClientHandler client : Server.clientList) {
                    if (client.name.equals(recipient) && client.logInStatus) {
                        client.dataOutputStream.writeUTF(this.name + " : " + MsgToSend);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.dataInputStream.close();
            this.dataOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
