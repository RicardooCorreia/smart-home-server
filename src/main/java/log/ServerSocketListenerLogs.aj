package log;

import domain.input.socket.ServerSocketListener;

import java.net.Socket;
import java.util.logging.Logger;

public aspect ServerSocketListenerLogs {
    private static final Logger LOGGER = Logger.getLogger(ServerSocketListener.class.getName());

    pointcut listenExecution(): execution(* ServerSocketListener.listen(..));
    pointcut clientSocketDispatchExecution() : execution(* ServerSocketListener.clientSocketDispatch(..));
    pointcut messageDispatchExecution(): execution(* dispatchMessage(..));

    before(): clientSocketDispatchExecution() {
        Socket client = (Socket) thisJoinPoint.getArgs()[1];
        LOGGER.info("Accepted new socket client: " + client);
    }

    before(): messageDispatchExecution() {
        LOGGER.info("Message from socket now being dispatched: " + thisJoinPoint.getArgs()[1]);
    }

    before(): listenExecution() {
        ServerSocketListener instance = (ServerSocketListener) thisJoinPoint.getThis();
        LOGGER.info("Socket server running at: " + instance.getSocket().toString());
    }
}
