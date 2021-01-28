package serverSocket;

import model.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Accepts connections and starts threads
 */
public class MultiEchoServer {
    private int port;
    private int timer;

    public MultiEchoServer(int port) {
        this.port = port;

    }

    /**
     * Creates Game main elements, accepts connections from Users and creates a thread associated for each one
     */
    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            Game game = new Game();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Server ready");
        while(true) {
            try {

                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted");
                executor.submit(new EchoServerClientHandler(socket));


            } catch (IOException e) {
                break;
            }


        }

        executor.shutdown();
    }
}