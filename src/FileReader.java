import java.io.IOException;
import java.io.File;
import org.apache.commons.io.FileUtils;

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
