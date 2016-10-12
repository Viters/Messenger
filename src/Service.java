import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by sir.viters on 12.10.2016.
 */
abstract class Service {
    private ObjectInputStream input;
    private ObjectOutputStream output;
    Socket connection;
    private Thread thread;

    protected void runService() {
        thread = new Thread(() -> {

            while (true) {
            try {
                this.connect();
                this.createStreams();
                this.receiveMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }

        });
        thread.start();
    }

    abstract protected void connect() throws IOException;

    private void createStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    private void receiveMessages() throws IOException {
        String message;
        do {
            try {
                message = input.readObject().toString();
                printWithDate(message);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        while (!connection.isClosed());
    }

    void sendMessage(String message) throws IOException {
        output.writeObject(message);
        output.flush();
        printWithDate("Message sent.");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        input.close();
        output.close();
        connection.close();
    }

    protected void printWithDate(String message) {
        String now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("[" + now + "] " + message);
    }
}
