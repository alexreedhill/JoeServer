import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    public String method;
    public String path;
    public String httpVersion;
    public String paramsString;
    public LinkedHashMap<String, String> params;
    public LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();

    public byte[] convertParamsToBytes() {
        return paramsString.getBytes();
    }
}
