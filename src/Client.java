import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * Created by sir.viters on 11.10.2016.
 */

public class Client {
    private int port;
    private String hostName;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private Thread thread;

    public Client(String hostName, int port) {
        this.port = port;
        this.hostName = hostName;

        this.thread = new Thread(() -> {
            try {
                runClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void connect() throws IOException {
        System.out.println("Connecting...");
        this.connection = new Socket(hostName, port);
        System.out.println("Connection established.");
    }

    private void createStreams() throws IOException {
        input = new ObjectInputStream(connection.getInputStream());
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
    }

    private void runClient() throws IOException {
        while (true) {
            this.connect();
            this.createStreams();
            this.receiveMessage();
        }
    }

    private void receiveMessage() throws IOException {
        String message;
        try {
            do {
                message = input.readObject().toString();
                System.out.println(message);
            }
            while (!connection.isClosed());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {
        output.writeObject(message);
        output.flush();
        System.out.println(message);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        input.close();
        output.close();
        connection.close();
    }
}
