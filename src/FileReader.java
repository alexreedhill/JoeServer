import java.io.IOException;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class FileReader {
    private static final Map<String, String> MIME_TYPES = createMimeTypes();

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

    public byte[] read(String path) throws IOException {
        File file = new File("./public/" + path);
        return FileUtils.readFileToByteArray(file);
    }

    public byte[] getDirectoryListing() throws Exception {
        StringBuilder builder = new StringBuilder();
        File directory = new File("./public/");
        File[] fileList = directory.listFiles();
        for(int i = 0; i < fileList.length; i++) {
            if(fileList[i].isFile()) {
                builder.append(fileList[i].getName());
                if((i + 1) != fileList.length) {
                    builder.append("\n");
                }
            }
        }
        return builder.toString().getBytes();
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
        String mimeType = MIME_TYPES.get(extension);
        return mimeType == null ? "text/plain" : mimeType;
    }
}
