import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by alexhill on 3/17/14.
 */
public class Response {
    private Request request;

    public Response(Request request) {
        this.request = request;
    }

    public String returnFullResponse() throws IOException {
        try {
            String fileContents = getFileContents();
            if(fileContents != null) {
                return "HTTP/1.0 200 OK\n\n" + fileContents + "\r\n";
            }
        } catch(FileNotFoundException ex) {
            System.err.println(ex);
        }
        return "HTTP/1.0 404 Not Found";
    }

    public String getFileContents() throws IOException {
        InputStream input = setupUrlInputStream();
        return buildFileContentsString(input);
    }

    private InputStream setupUrlInputStream() throws IOException {
        String filename = getFilename();
        String absolutePath = System.getProperty("user.dir").replace("/src", "");
        URL url = new URL("file:" + absolutePath + "/public" + filename);
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
        return this.request.path;
    }
}
