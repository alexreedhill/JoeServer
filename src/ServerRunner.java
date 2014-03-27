
public class ServerRunner {

    public static void main(String[] args)  {
        try {
            Server server = new Server(5000);
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


