import org.junit.Test;
import java.io.IOException;
import static junit.framework.Assert.assertEquals;

public class FileReaderTest {
    private FileReader fileReader = new FileReader();

    @Test
    public void itReadsPlainTextFileIntoByteArray() throws IOException {
        byte[] bytes = fileReader.read("/file1");
        String contents = new String(bytes, "UTF-8");
        assertEquals("file1 contents", contents);
    }

    @Test public void itGetsMimeTypeForJpegImage() throws IOException {
        String MIMEtype = fileReader.getMimeType("/image.jpeg");
        assertEquals("image/jpeg", MIMEtype);
    }
}
