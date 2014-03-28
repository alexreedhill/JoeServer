import java.io.IOException;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class FileReader {
    private Request request;
    private String publicPath;

    private static Map<String, String> createMimeTypes() {
        Map<String, String> mimeTypes = new HashMap<String, String>();
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("jpg", "image/jpg");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("html", "text/html");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("js", "text/javascript");
        return Collections.unmodifiableMap(mimeTypes);
    }

    public FileReader(Request request) {
        this.request = request;
        this.publicPath = request.publicPath;
    }

    public FileReader() {
        publicPath = "../cob_spec/public/";
    }

    public byte[] read() throws IOException {
        File file = new File(publicPath + request.path);
        return FileUtils.readFileToByteArray(file);
    }

    public byte[] read(String path) throws IOException {
        File file = new File(publicPath + path);
        return FileUtils.readFileToByteArray(file);
    }

    public File[] getDirectoryLinks() throws Exception {
        File directory = new File(publicPath);
        return directory.listFiles();
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
        Map<String, String> MIME_TYPES = createMimeTypes();
        String mimeType = MIME_TYPES.get(extension);
        return mimeType == null ? "text/plain" : mimeType;
    }
}
