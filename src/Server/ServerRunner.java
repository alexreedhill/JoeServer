package Server;

import java.io.IOException;

public class ServerRunner {

    public static void main(String[] args)  {
        try {
            startServer(args);
            sleep();
            System.out.println("Stopping Server.Server");
        } catch(Exception ex) {
            System.err.println(ex);
        }
    }

    private static void startServer(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        String publicPath = args[1];
        Server server = new Server(port, publicPath);
        new Thread(server).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(10 * 10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


