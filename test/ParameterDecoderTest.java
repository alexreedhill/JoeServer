import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class ParameterDecoderTest {
    private ParameterDecoder decoder;

    @Test
    public void decodesMyName() {
        String string = "Alexander%20R%2E%20Hill%21";
        decoder = new ParameterDecoder(string);
        decoder.decodeString();
        assertEquals("Alexander R. Hill!", decoder.getParams());
    }

    @Test
    public void decodesStringsExpectedByCobSpec() {
        String string = "Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F";
        decoder = new ParameterDecoder(string);
        decoder.decodeString();
        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", decoder.getParams());
    }

    @Test
    public void returnsHashMapWithParamValues() {
        String string = "variable_1=stuff&variable_2=more_stuff";
        decoder = new ParameterDecoder(string);
        HashMap<String, String> paramsHash = decoder.decode();
        assertEquals(paramsHash.get("variable_1"), "stuff");
        assertEquals(paramsHash.get("variable_2"), "more_stuff");
    }
}
