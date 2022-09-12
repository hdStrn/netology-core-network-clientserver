package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final Scanner SCAN = new Scanner(System.in);

    public static void main(String[] args) {
        String host = "netology.homework";
        int port = 8080;
        try(Socket clientSocket = new Socket(host, port);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String response = "";
            while (!response.endsWith("Bye!")) {
                response = in.readLine();
                System.out.println(response);
                if (response.endsWith("?")) {
                    String message = SCAN.nextLine();
                    out.println(message);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
