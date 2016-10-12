import java.io.IOException;
import java.net.Socket;

/**
 * Created by sir.viters on 11.10.2016.
 */

class Client extends Service {
    private int port;
    private String hostName;

    Client(String hostName, int port) {
        this.port = port;
        this.hostName = hostName;

        runService();
    }

    protected final void connect() throws IOException {
        System.out.println("Connecting...");
        this.connection = new Socket(hostName, port);
        System.out.println("Connection established.");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
