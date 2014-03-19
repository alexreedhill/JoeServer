import java.io.IOException;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class FileReader {
    private static final Map<String, String> MIME_TYPES = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("jpeg", "image/jpeg");
        result.put("jpg", "image/jpg");
        result.put("png", "image/png");
        result.put("html", "text/html");
        result.put("css", "text/css");
        result.put("js", "text/javascript");
        return Collections.unmodifiableMap(result);
    }

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
        String mimeType;
        if ((mimeType = MIME_TYPES.get(extension)) != null) {
            return mimeType;
        } else {
            return "text/plain";
        }
    }
}
