package Server;

import java.io.IOException;

public class ServerRunner {
    private static int port;
    private static String publicPath;

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
        try {
            port = Integer.parseInt(args[0]);
            publicPath = args[1];
        } catch(ArrayIndexOutOfBoundsException ex) {
            port = 5000;
            publicPath = "../cob_spec/public";
        }
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


