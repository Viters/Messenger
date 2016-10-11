import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sir.viters on 11.10.2016.
 */
public class Server {
    private int port;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ServerSocket socket;
    private Socket connection;
    private Thread thread;

    public Server(int port) throws IOException {
        this.port = port;
        socket = new ServerSocket(port);
        thread = new Thread(() -> {
            try {
                runServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    private void runServer() throws IOException {
        while(true) {
            connect();
            createStreams();
            onChat();
        }
    }

    private void connect() throws IOException {
        System.out.println("Waiting for connection...");
        connection = socket.accept();
        System.out.println("Connection established.");
    }

    private void createStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    private void onChat() throws IOException {
        System.out.println("You are now connected!");
        String message;
        do {
            try {
                message = input.readObject().toString();
                System.out.println(message);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        while(!connection.isClosed());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        output.close();
        input.close();
        connection.close();
    }

    public void sendMessage(String message) throws IOException {
        output.writeObject(message);
        output.flush();
        System.out.println(message);
    }
}
