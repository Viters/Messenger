import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String userInput = null;
        boolean repeatLoop = true;

        System.out.println("Do you want to host or join server? (host/join)");

        while (repeatLoop) {
            userInput = input.next();
            repeatLoop = !(Objects.equals(userInput, "host") || Objects.equals(userInput, "join"));
        }

        switch (userInput) {
            case "host":
                goServer();
                break;
            case "join":
                goClient();
                break;
        }
    }

    private static void goServer() {
        Scanner input = new Scanner(System.in);
        int port;
        String message;

        System.out.println("Please provide port to start your server:");
        port = input.nextInt();

        try {
            Server server = new Server(port);

            while (true) {
                message = input.nextLine();
                server.sendMessage(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void goClient() {
        Scanner input = new Scanner(System.in);
        int port;
        String host;
        String message;

        System.out.println("Please provide host:");
        host = input.next();
        System.out.println("Please provide port:");
        port = input.nextInt();

        try {
            Client client = new Client(host, port);

            while (true) {
                message = input.nextLine();
                client.sendMessage(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
