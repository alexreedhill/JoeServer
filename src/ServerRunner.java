public class ServerRunner {

    public static void main(String[] args)  {
        try {
            int port = Integer.parseInt(args[0]);
            String publicPath = args[1];
            Server server = new Server(port, publicPath);
            new Thread(server).start();
            try {
                Thread.sleep(10 * 10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Stopping Server");
            server.stop();
        } catch(Exception ex) {
            System.err.println(ex);
        }

    }
}


