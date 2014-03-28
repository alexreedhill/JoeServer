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
        file = new File("/Users/alexhill/cob_spec/public/form");
    }

    public ResponseBuilder createNewFile() throws IOException {
        file.createNewFile();
        builder.buildOKResponse();
        write();
        return builder;
    }

    public void write() throws IOException {
        PrintWriter writer = new PrintWriter(file);
        String[] fileContents = request.body.split("=");
        writer.write(fileContents[0] + " = " + fileContents[1]);
        writer.close();
    }
}
