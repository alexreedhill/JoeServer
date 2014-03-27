import java.util.LinkedHashMap;
import java.util.Map;

public class ParameterDecoder {
    private static final LinkedHashMap<String, Character> CONVERSIONS = createConversions();
    private String params;
    private LinkedHashMap<String, String> paramsHash = new LinkedHashMap<String, String>();

    public ParameterDecoder(String params) {
        this.params = params;
    }

    public LinkedHashMap<String, String> decode() {
        splitParamsIntoKeyAndValue();
        decodeString();
        return paramsHash;
    }

    private void splitParamsIntoKeyAndValue() {
        String[] splitParams =  params.split("&");
        for (String param : splitParams) {
            splitParam(param);
        }
    }

    private void splitParam(String param) {
        try {
            String[] splitParam = param.split("=", 2);
            paramsHash.put(splitParam[0], splitParam[1]);
        } catch(ArrayIndexOutOfBoundsException ex) {}

    }

    private void decodeString() {
        for(Map.Entry<String, String> entry : paramsHash.entrySet()) {
            String value = entry.getValue();
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if(c == '%') {
                    decodeSubString(i, entry, value);
                    value = entry.getValue();
                }
            }
        }
    }

    private void decodeSubString(int i, Map.Entry<String, String> entry, String value) {
        StringBuilder decodedStringBuilder = new StringBuilder(value);
        String encodedSubString = value.substring(i, i + 3);
        String decodedSubString = CONVERSIONS.get(encodedSubString).toString();
        decodedStringBuilder.replace(i, i + 3, decodedSubString);
        entry.setValue(decodedStringBuilder.toString());
    }

    private static LinkedHashMap<String, Character> createConversions() {
        LinkedHashMap<String, Character> conversions = new LinkedHashMap<String, Character>();
        conversions.put("%20", ' ');
        conversions.put("%21", '!');
        conversions.put("%22", '"');
        conversions.put("%3C", '<');
        conversions.put("%3E", '>');
        conversions.put("%3D", '=');
        conversions.put("%3B", ';');
        conversions.put("%2B", '+');
        conversions.put("%2D", '-');
        conversions.put("%2A", '*');
        conversions.put("%26", '&');
        conversions.put("%40", '@');
        conversions.put("%23", '#');
        conversions.put("%24", '$');
        conversions.put("%5B", '[');
        conversions.put("%5D", ']');
        conversions.put("%3A", ':');
        conversions.put("%3F", '?');
        conversions.put("%2E", '.');
        conversions.put("%2C", ',');
        return conversions;
    }
}
