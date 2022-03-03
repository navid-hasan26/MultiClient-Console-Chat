package com.navid026.ConsoleChat.Socket.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {
    final static int serverPort = 5000;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        InetAddress ip = InetAddress.getByName("localhost") ;

        Socket socket = new Socket(ip, serverPort);

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    String message = scanner.nextLine();
                    try {
                        dataOutputStream.writeUTF(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        String message = dataInputStream.readUTF();
                        System.out.println(message);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();

    }
}
