package domain.input.socket;

import database.SensorRepository;
import middleware.socket.SocketMessageParser;

import java.io.IOException;
import java.util.logging.Logger;

import static utils.Properties.SERVER_SOCKET_PORT;

public class SocketJob implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(SocketJob.class.getName());

    private void saveToDatabase(String sensor) {
        SensorRepository.getInstance().save(SocketMessageParser.parse(sensor));
    }

    @Override
    public void run() {
        int currentPort = SERVER_SOCKET_PORT;
        while (!startServerAtPort(currentPort)) {
            currentPort++;
        }
    }

    private boolean startServerAtPort(int port) {
        try {
            new ServerSocketListener(port).listen(this::saveToDatabase);
            return true;
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
        return false;
    }
}
