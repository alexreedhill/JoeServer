import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ParameterDecoderTest {
    private ParameterDecoder decoder = new ParameterDecoder();

    @Test
    public void decodesMyName() {
        String string = "Alexander%20R%2E%20Hill%21";
        String decodedName = decoder.decodeString(string);
        assertEquals("Alexander R. Hill!", decodedName);
    }

    @Test
    public void decodesStringsExpectedByCobSpec() {
        String string = "Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F";
        String decodedString = decoder.decodeString(string);
        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", decodedString);
    }

    @Test
    public void returnsHashMapWithParamValues() {
        String string = "variable_1=stuff&variable_2=more_stuff";
        HashMap<String, String> params = decoder.decode(string);
        assertEquals(params.get("variable_1"), "stuff");
        assertEquals(params.get("variable_2"), "more_stuff");
    }
}
