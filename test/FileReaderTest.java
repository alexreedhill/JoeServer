import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;

public class FileReaderTest {
    private FileReader fileReader = new FileReader();

    @Test
    public void readsPlainTextFileIntoByteArray() throws IOException {
        byte[] bytes = fileReader.read("file1");
        String contents = new String(bytes, "UTF-8");
        assertEquals("file1 contents", contents);
    }

    @Test
    public void getsMimeTypeForJpegImage() throws IOException {
        String mimeType = fileReader.getMimeType("image.jpeg");
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
}
