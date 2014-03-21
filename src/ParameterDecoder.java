import java.util.HashMap;

public class ParameterDecoder {
    private static final HashMap<String, Character> CONVERSIONS = createConversions();
    private String params;
    private HashMap<String, String> paramsHash;

    public ParameterDecoder(String params) {
        this.params = params;
    }

    public HashMap<String, String> decode() {
        decodeString();
        splitParams();
        return paramsHash;
    }

    public String getParams() {
        return params;
    }

    public void decodeString() {
        for (int i = 0; i < params.length(); i++) {
            char c = params.charAt(i);
            if(c == '%') {
                decodeSubString(i);
            }
        }
    }

    private void decodeSubString(int i) {
        StringBuilder decodedString = new StringBuilder(params);
        String encodedSubString = params.substring(i, i + 3);
        String decodedSubString = CONVERSIONS.get(encodedSubString).toString();
        decodedString.replace(i, i + 3, decodedSubString);
        params = decodedString.toString();
    }

    private void splitParams() {
        paramsHash = new HashMap<String, String>();
        String[] splitParams =  params.split("&");
        for (String param : splitParams) {
            splitParam(param);
        }
    }

    private void splitParam(String param) {
        String[] splitParam = param.split("=");
        paramsHash.put(splitParam[0], splitParam[1]);
    }

    private static HashMap<String, Character> createConversions() {
        HashMap<String, Character> conversions = new HashMap<String, Character>();
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
