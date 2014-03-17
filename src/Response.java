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

    public String returnFullResponse() throws IOException {
        String fileContents = getFileContents();
        String fullResponse = "HTTP/1.0 200 OK\n\n" + fileContents + "\r\n";
        return fullResponse;
    }

    public String getFileContents() throws IOException {
        InputStream input = setupUrlInputStream();
        return buildFileContentsString(input);
    }

    private InputStream setupUrlInputStream() throws IOException {
        String filename = getFilename();
        URL url = new URL("file:public" + filename);
        URLConnection urlConnection = url.openConnection();
        return urlConnection.getInputStream();
    }

    private String buildFileContentsString(InputStream input) throws IOException {
        StringBuilder fileContents = new StringBuilder();
        int data = input.read();
        while(data != -1){
            fileContents.append((char) data);
            data = input.read();
        }
        return fileContents.toString();
    }

    private String getFilename() {
        return this.request.split(" ")[1];
    }
}
