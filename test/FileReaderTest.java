import org.junit.Test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;

public class FileReaderTest {
    private FileReader fileReader = new FileReader();

    @Test
    public void readsPlainTextFileIntoByteArray() throws IOException {
        byte[] bytes = fileReader.read("/file1");
        String contents = new String(bytes, "UTF-8");
        assertEquals("file1 contents", contents);
    }

    @Test
    public void getsMimeTypeForJpegImage() throws IOException {
        String mimeType = fileReader.getMimeType("/image.jpeg");
        assertEquals("image/jpeg", mimeType);
    }

    @Test
    public void getsPlainTextMimeTypeForFileWithNoExtension() throws IOException {
        String mimeType = fileReader.getMimeType("file1");
        assertEquals("text/plain", mimeType);
    }

    @Test
    public void getsCorrectMimeTypeForGif() throws IOException {
        String mimeType = fileReader.getMimeType("image.png");
        assertEquals("image/png", mimeType);
    }

    @Test
    public void createsDirectoryLinks() throws Exception {
        byte[] directoryLinksBytes = fileReader.getDirectoryLinks();
        String directoryLinks = new String(directoryLinksBytes, "UTF-8");
        assertEquals("<a href=\"/file1\">file1</a>\n" +
                     "<a href=\"/file2\">file2</a>\n" +
                     "<a href=\"/image.gif\">image.gif</a>\n" +
                     "<a href=\"/image.jpeg\">image.jpeg</a>\n" +
                     "<a href=\"/image.png\">image.png</a>\n" +
                     "<a href=\"/partial_content.txt\">partial_content.txt</a>\n" +
                     "<a href=\"/text-file.txt\">text-file.txt</a>\n", directoryLinks);
    }
}
