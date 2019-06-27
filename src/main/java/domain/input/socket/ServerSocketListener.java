package domain.input.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static utils.Properties.CHARSET_NAME;

public class ServerSocketListener {
    private static final Logger LOGGER = Logger.getLogger(ServerSocketListener.class.getName());

    private final ServerSocket socket;

    private ServerSocketListener(ServerSocket socket) {
        this.socket = socket;
    }

    ServerSocketListener(int port) throws IOException {
        this(new ServerSocket(port));
    }

    void listen(Consumer<String> messageConsumer) throws IOException {
        while (!Thread.currentThread().isInterrupted()) {
            Socket clientSocket = socket.accept();
            new Thread(() -> clientSocketDispatch(messageConsumer, clientSocket)).start();
        }
    }

    private void clientSocketDispatch(Consumer<String> messageConsumer, Socket clientSocket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),
                    Charset.forName(CHARSET_NAME)));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                dispatchMessage(messageConsumer, inputLine);
            }
            bufferedReader.close();
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.warning("Problem with client socket input stream, socket:" + clientSocket.toString());
        }
    }

    private void dispatchMessage(Consumer<String> messageConsumer, String inputLine) {
        messageConsumer.accept(inputLine);
    }

    public ServerSocket getSocket() {
        return socket;
    }
}
