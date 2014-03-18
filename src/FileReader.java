import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

/**
 * Created by alexhill on 3/18/14.
 */
public class FileReader {

    public byte[] read(String path) throws IOException {
        File file = new File("./public/" + path);
        return FileUtils.readFileToByteArray(file);
    }

    public String getMimeType(String path) throws IOException {
        try {
            String extension = path.split("\\.")[1];
            return getMimeTypeFromFileExtension(extension);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return "text/plain";
        }
    }

    private String getMimeTypeFromFileExtension(String extension) {
        if (extension.equals("jpeg")) {
            return "image/jpeg";
        } else {
            return "text/plain";
        }
    }
}
