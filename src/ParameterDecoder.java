import java.util.HashMap;

/**
 * Created by alexhill on 3/20/14.
 */
public class ParameterDecoder {
    private static final HashMap<String, Character> CONVERSIONS = createConversions();

    public String decode(String string) {
        for (int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            if(c == '%') {
                StringBuilder decodedString = new StringBuilder(string);
                String encodedSubString = string.substring(i, i + 3);
                String decodedSubString = CONVERSIONS.get(encodedSubString).toString();
                decodedString.replace(i, i + 3, decodedSubString);
                string = decodedString.toString();
            }
        }
        return string;
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
