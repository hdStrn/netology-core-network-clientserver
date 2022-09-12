package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        System.out.println("Server started..");
        int port = 8080;
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(port);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter outcome = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader income = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                System.out.println("New connection accepted");
                meeting(clientSocket, outcome, income);
                int points = ripples(clientSocket, outcome, income);
                String result = checkResult(points);
                outcome.println(
                        String.format("Your result is %d from 2. %s Hope to see you again next time! Bye!", points, result));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void meeting(Socket clientSocket, PrintWriter outcome, BufferedReader income) throws IOException {
        outcome.println("Hello, human, who are you?");
        String name = income.readLine();
        System.out.println("message from " + clientSocket.getLocalAddress() + ": " + name);
        outcome.println("Nice to meet you, " + name + "!\n");
        outcome.println("Ok, " + name + ", how old are you?");
        String age;
        while (true) {
            age = income.readLine();
            System.out.println("message from " + clientSocket.getLocalAddress() + ": " + age);
            try {
                int ageInt = Integer.parseInt(age);
                break;
            } catch (Exception e) {
                outcome.println("Hm, can't understand, could you repeat please?");
            }
        }
        outcome.println("Ahh, " + age + ", good time to make your dreams come true!\n");
    }

    public static int ripples(Socket clientSocket, PrintWriter outcome, BufferedReader income) throws IOException {
        outcome.println("I'll make you two riddles, try to guess them both:\n");

        String ripple1 = """
                        Lives in seas and rivers.
                        His hands are like two pincers.
                        As round as a cab.
                        Who is it? — It’s a ...?""";
        outcome.println(ripple1);
        String answer1 = income.readLine();
        System.out.println("message from " + clientSocket.getLocalAddress() + ": " + answer1);
        int result = 0;
        if ("crab".equalsIgnoreCase(answer1)) {
            outcome.println("\nNice one! Guess another ripple:\n");
            result++;
        } else {
            outcome.println("\nNot correct, but you can try for the next ripple:\n");
        }

        String ripple2 = """
                        A lot of spots.
                        A long, long neck
                        A funny scarf
                        It's a ...?""";
        outcome.println(ripple2);
        String answer2 = income.readLine();
        System.out.println("message from " + clientSocket.getLocalAddress() + ": " + answer2);
        if ("giraffe".equalsIgnoreCase(answer2)) {
            outcome.println("\nCorrect!\n");
            result++;
        } else {
            outcome.println("\nUnfortunately, not :(\n");
        }
        return result;
    }

    public static String checkResult(int points) {
        if (points == 0) {
            return "Hm, at least try to read something about animals :)";
        } else if (points == 1) {
            return "I see that you know something about animals :)";
        } else {
            return "You know everything about animals!";
        }
    }
}
