import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by sir.viters on 12.10.2016.
 */
abstract class Service {
    private ObjectInputStream input;
    private ObjectOutputStream output;
    Socket connection;
    private Thread thread;

    protected void runService() {
        this.thread = new Thread(() -> {
            try {
                while (true) {
                    this.connect();
                    this.createStreams();
                    this.receiveMessages();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    abstract protected void connect() throws IOException;

    private void createStreams() throws IOException {
        input = new ObjectInputStream(connection.getInputStream());
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
    }

    private void receiveMessages() throws IOException {
        String message;
        do {
            try {
                message = input.readObject().toString();
                System.out.println(message);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        while (!connection.isClosed());
    }

    void sendMessage(String message) throws IOException {
        output.writeObject(message);
        output.flush();
        System.out.println("Message sent.");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        input.close();
        output.close();
        connection.close();
    }
}
