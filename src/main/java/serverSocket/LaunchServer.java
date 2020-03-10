package serverSocket;

/**
 * Launches Server
 */
public class LaunchServer {
    private static int timer=30;

    /**
     * Launches Server and sets timer
     * @param args command line arguments, args[0] is the time in seconds to wait before starting the game
     */
    public static void main(String[] args){
        //timer=Integer.parseInt(args[0]);
        MultiEchoServer server = new MultiEchoServer(8079);
        server.startServer();

    }

    public static int getTimer() {
        return timer;
    }

}