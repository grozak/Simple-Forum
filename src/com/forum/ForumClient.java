package com.forum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ForumClient {

    public void runClient() {
        try {
            Socket socket = new Socket("127.0.0.1", 4242);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            Scanner scanner=new Scanner(System.in);
            String message="";
            String serverMessage;
            String flag="6bd4c852bd8226b5d8bde1be03c393389daaae076ba41f417e108390510b69c6";


            System.out.println(input.readLine());
            while(!message.equals("Koniec")) {
                while(true) {
                    serverMessage = input.readLine();
                    if(serverMessage.equals(flag))
                        break;
                    System.out.println(serverMessage);
                }
                message=scanner.nextLine();
                output.println(message);
                output.flush();
            }
        }catch(SocketException e) {
            System.out.println("Brak połączenia z serwerem");
        }
        catch (IOException e) {
            System.out.println("Błąd wejścia/wyjścia");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ForumClient client=new ForumClient();
        client.runClient();
    }
}