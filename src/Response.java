import java.awt.image.BufferedImage;
import java.io.*;
import org.apache.commons.io.FileUtils;
import javax.imageio.ImageIO;

/**
 * Created by alexhill on 3/17/14.
 */
public class Response {
    private Request request;
    private String fullResponse;

    public Response(Request request) {
        this.request = request;
    }

    public String respond(OutputStream output) throws IOException {
        String fileExtension = getFileExtension();
        try {
            byte[] bytes = getFileByteArray();
            if (fileExtension.equals("jpeg")) {
                respondWithImage(bytes, output);
            } else {
                respondWithFile(bytes, output);
            }
        } catch(FileNotFoundException ex) {
            fullResponse = "HTTP/1.0 404 Not Found";
            PrintWriter out = new PrintWriter(output, true);
            out.println(fullResponse);
        }
        return fullResponse;
    }

    public String getFileExtension() throws IOException {
        try {
            return request.path.split(".")[-1];
        } catch(ArrayIndexOutOfBoundsException ex) {
            return "";
        }

    }

    public void respondWithFile(byte[] bytes, OutputStream output) throws IOException {
        String fileContents = buildFileContentsString(bytes);
        if(fileContents != null) {
            fullResponse = "HTTP/1.0 200 OK\n\n" + fileContents + "\r\n";
        } else {
            fullResponse = "HTTP/1.0 200 OK\r\n";
        }
        PrintWriter out = new PrintWriter(output, true);
        out.println(fullResponse);
    }

    public void respondWithImage(byte[] bytes, OutputStream output) throws IOException {
        BufferedImage image = buildBufferedImage(bytes);
        ImageIO.write(image, "jpeg", output);
    }

    public String getStatusCode() throws Exception {
        return fullResponse.split(" ")[1];
    }

    private byte[] getFileByteArray() throws IOException {
        File file = new File("./public/" + request.path);
        return FileUtils.readFileToByteArray(file);
    }

    private BufferedImage buildBufferedImage(byte[] bytes) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        return ImageIO.read(input);
    }

    private String buildFileContentsString(byte[] bytes) throws IOException {
        return new String(bytes, "UTF-8");
    }

}

// BufferedImage
// Image.IO.write(image, "png", outputStream);