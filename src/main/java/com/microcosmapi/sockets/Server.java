package sockets;

import sockets.contract.HttpMethod;
import sockets.contract.RequestRunner;
import sockets.http.HttpHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * Simple Server: accepts HTTP connections and responds using
 * the Java net socket library.
 *  - Blocking approach ( 1 request per thread )
 *  - Non-blocking ( Investigate Java NIO - new IO, Netty uses this? )
 */
public class Server {
    /*
     * defines the port
     * adds endpoints / routes
     * handles lifecycle of Request / Response ???
     */
    private final Map<String, RequestRunner> routes;
    private final ServerSocket socket;
    private final Executor threadPool;
    private HttpHandler handler;

    public Server(int port) throws IOException {
        routes = new HashMap<>();
        threadPool = Executors.newFixedThreadPool(100);
        socket = new ServerSocket(port);
    }

    public void start() throws IOException {
        handler = new HttpHandler(routes);

        while (true) {
            Socket clientConnection = socket.accept();
            handleConnection(clientConnection);
        }
    }

    /*
     * Capture each Request / Response lifecycle in a thread
     * Execute in threadPool
     */
    private void handleConnection(Socket clientConnection) {
        // Wrapped in a runnable to avoid requests blocking each other
        Runnable httpRequestRunner = () -> {
            try {
                handler.handleConnection(clientConnection.getInputStream(), clientConnection.getOutputStream());
            } catch (IOException ignored) {
            }
        };
        threadPool.execute(httpRequestRunner);
    }

    /*
     * adds routes and endpoints to the application
     */
    public void addRoute(final HttpMethod opCode, final String route, final RequestRunner runner) {
        /*
         * combination of op code from GET request and URI
         */
        routes.put(opCode.name().concat(route), runner);
    }
}