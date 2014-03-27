import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {
    private Request request;
    private ResponseBuilder builder;
    private File file;

    public FileWriter(Request request, ResponseBuilder builder) {
        this.request = request;
        this.builder = builder;
    }

    public ResponseBuilder createNewFile() throws IOException {
        String fullPath = request.publicPath + request.path;
        file = new File("/Users/alexhill/cob_spec/public/form");
        if (file.createNewFile()) {
            builder.buildOKResponse();
            System.out.println("File created: " + fullPath);
        } else {
            builder.buildConflictResponse();
            System.out.println("Failed to create file: " + fullPath);
        }
        builder = write();
        return builder;
    }

    public ResponseBuilder write() throws IOException {
        PrintWriter writer = new PrintWriter(file);
        writer.write(request.body);
        writer.close();
        return builder;
    }
}
