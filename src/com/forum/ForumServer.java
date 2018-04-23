package com.forum;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ForumServer {

    public void run() {
        System.out.println("Start serwera");
        try {
            ServerSocket serverSocket = new ServerSocket(4242);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Podłączono użytkownika");

                String flag = "6bd4c852bd8226b5d8bde1be03c393389daaae076ba41f417e108390510b69c6";

                Forum forum;

                Reader reader = new Reader();
                forum = reader.open("backup\\forumBackup.txt");

                try {
                    forum.runForum(output, in, flag);
                } catch (SocketException e) {
                    System.out.println("Utracono połączenie z użytkownikiem");
                }
                    System.out.println("Odłączono użytkownika");

                    forum.save("backup\\forumBackup.txt");
                    output.close();
                    in.close();
                }
        } catch (IOException e) {
            System.out.println("Błąd wejścia/wyjścia");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Nie znaleziono klasy Forum");
        }
    }


    public static void main(String[] args) {
        ForumServer forumServer = new ForumServer();
        forumServer.run();
    }
}