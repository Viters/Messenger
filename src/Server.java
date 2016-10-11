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

    public Server(int port) throws IOException {
        this.port = port;
        socket = new ServerSocket(port);
    }

    public void run() throws IOException {
        while(true) {
            connect();
            createStreams();
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

    }
}
