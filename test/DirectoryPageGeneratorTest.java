import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class DirectoryPageGeneratorTest {

    private FileReader fileReader = new FileReader();
    private DirectoryPageGenerator generator = new DirectoryPageGenerator(fileReader);

    @Test
    public void generatesDirectoryPage() throws Exception {
        byte[] directoryPage = generator.generate();
        assertEquals("<a href=\"/file1\">file1</a>\n" +
                "<a href=\"/file2\">file2</a>\n" +
                "<a href=\"/image.gif\">image.gif</a>\n" +
                "<a href=\"/image.jpeg\">image.jpeg</a>\n" +
                "<a href=\"/image.png\">image.png</a>\n" +
                "<a href=\"/partial_content.txt\">partial_content.txt</a>\n" +
                "<a href=\"/text-file.txt\">text-file.txt</a>\n", new String(directoryPage, "UTF-8"));
    }
}
