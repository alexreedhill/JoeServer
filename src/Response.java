import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by alexhill on 3/17/14.
 */
public class Response {
    private String request;

    public Response(String request) {
        this.request = request;
    }

    public String getFileContents() throws IOException {
        String filename = getFilename();
        URL url = new URL("file:public" + filename);
        URLConnection urlConnection = url.openConnection();
        InputStream input = urlConnection.getInputStream();
        StringBuilder fileContents = new StringBuilder();
        int data = input.read();
        while(data != -1){
            System.out.print((char) data);
            fileContents.append((char) data);
            data = input.read();
        }
        return fileContents.toString();
    }

    public String getFilename() {
        return this.request.split(" ")[1];
    }
}
