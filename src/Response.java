import java.awt.image.BufferedImage;
import java.io.*;
import org.apache.commons.io.FileUtils;
import javax.imageio.ImageIO;

/**
 * Created by alexhill on 3/17/14.
 */
public class Response {
    private Request request;
    private FileReader fileReader;
    private String statusCode;
    private byte[] fullResponse;
    private byte[] body;

    public Response(Request request) throws IOException {
        this.request = request;
        fileReader = new FileReader();
        try {
            this.body = fileReader.read(request.path);
            this.statusCode = "200";
        } catch(IOException ex) {
            this.statusCode = "404";
        }
    }

    public byte[] respond() throws IOException {
        if(request.path.equals("/")) {
            return (request.httpVersion + " 200 OK\r\n").getBytes();
        } else if(this.statusCode.equals("404")) {
            return (request.httpVersion + " 404 Not Found\r\n").getBytes();
        } else {
            return buildFullResponse();
        }
    }

    public byte[] buildFullResponse() throws IOException {
        byte[] metadata = (buildStatusLine() + buildContentTypeHeader()).getBytes();
        fullResponse = new byte[metadata.length + body.length];
        System.arraycopy(metadata, 0, fullResponse, 0, metadata.length);
        System.arraycopy(body, 0, fullResponse, metadata.length, body.length);
        System.out.println(new String(fullResponse));

        return fullResponse;
    }

    public String buildStatusLine() {
        return request.httpVersion + " " + "200 " + "OK\r\n";
    }

    public String buildContentTypeHeader() throws IOException {
        return "Content-Type: " + fileReader.getMimeType(request.path) + "\r\n\n";
    }

}

// BufferedImage
// Image.IO.write(image, "png", outputStream);