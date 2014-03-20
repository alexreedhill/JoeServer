import java.util.HashMap;

public class ParameterDecoder {
    private static final HashMap<String, Character> CONVERSIONS = createConversions();

    public HashMap<String, String> decode(String params) {
        String decodedParams = decodeString(params);
        return splitParams(decodedParams);
    }

    public String decodeString(String string) {
        for (int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            if(c == '%') {
                string = decodeSubString(string, i);
            }
        }
        return string;
    }

    private String decodeSubString(String string, int i) {
        StringBuilder decodedString = new StringBuilder(string);
        String encodedSubString = string.substring(i, i + 3);
        String decodedSubString = CONVERSIONS.get(encodedSubString).toString();
        decodedString.replace(i, i + 3, decodedSubString);
        return decodedString.toString();
    }

    private HashMap<String, String> splitParams(String stringParams) {
        HashMap<String, String> params = new HashMap<String, String>();
        String[] splitParams =  stringParams.split("&");
        for(int i = 0; i < splitParams.length; i++) {
            splitParam(params, splitParams, i);
        }
        return params;
    }

    private void splitParam(HashMap<String, String> params, String[] splitParams, int i) {
        String param = splitParams[i];
        String[] splitParam = param.split("=");
        params.put(splitParam[0], splitParam[1]);
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
