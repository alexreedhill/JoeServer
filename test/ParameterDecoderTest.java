import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by alexhill on 3/20/14.
 */
public class ParameterDecoderTest {
    private ParameterDecoder decoder = new ParameterDecoder();

    @Test
    public void itDecodesMyName() {
        String string = "Alexander%20R%2E%20Hill%21";
        String decodedName = decoder.decode(string);
        assertEquals("Alexander R. Hill!", decodedName);
    }

    @Test
    public void itDecodesStringsExpectedByCobSpec() {
        String string = "Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F";
        String decodedString = decoder.decode(string);
        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", decodedString);
    }
}
