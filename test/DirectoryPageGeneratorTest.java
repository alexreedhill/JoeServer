import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class DirectoryPageGeneratorTest {
    private DirectoryPageGenerator generator;

    @Before
    public void setupDependencies() {
        Request request = new Request();
        request.publicPath = "../cob_spec/public/";
        FileReader fileReader = new FileReader(request);
        generator = new DirectoryPageGenerator(fileReader);
    }

    @Test
    public void generatesDirectoryPage() throws Exception {
        byte[] directoryPage = generator.generate();
        assertEquals("<a href=\"/file1\">file1</a><br>\n" +
                "<a href=\"/file2\">file2</a><br>\n" +
                "<a href=\"/image.gif\">image.gif</a><br>\n" +
                "<a href=\"/image.jpeg\">image.jpeg</a><br>\n" +
                "<a href=\"/image.png\">image.png</a><br>\n" +
                "<a href=\"/partial_content.txt\">partial_content.txt</a><br>\n" +
                "<a href=\"/text-file.txt\">text-file.txt</a><br>\n", new String(directoryPage, "UTF-8"));
    }
}
