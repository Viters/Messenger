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

    public Client(String hostName, int port){
        this.port = port;
        this.hostName = hostName;
    }

    private void connect() throws IOException{
        System.out.println("Connecting...");
        this.connection = new Socket(hostName, port);
        System.out.println("Connection established.");
    }

    private void createStreams() throws IOException{
        input = new ObjectInputStream(connection.getInputStream());
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
    }

    private void runClient() throws IOException{
        this.connect();
        this.createStreams();
    }

    @Override
    protected void finalize() throws Throwable, IOException{
        super.finalize();
        input.close();
        output.close();
        connection.close();
    }
}
