import java.io.File;

public class DirectoryPageGenerator implements PageGenerator {
    private FileReader fileReader;
    private StringBuilder builder = new StringBuilder();

    public DirectoryPageGenerator(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public byte[] generate() throws Exception {
        File[] fileList = fileReader.getDirectoryLinks();
        for (File file : fileList) {
            if (file.isFile()) {
                builder = buildDirectoryLink(file);
            }
        }
        return builder.toString().getBytes();
    }

    public StringBuilder buildDirectoryLink(File file) {
        String filename = file.getName();
        builder.append("<a href=\"/");
        builder.append(filename);
        builder.append("\">");
        builder.append(filename);
        builder.append("</a>\n");
        return builder;
    }
}
