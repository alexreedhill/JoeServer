import java.util.LinkedHashMap;

public class Request {
    public String method;
    public String path;
    public String httpVersion;
    public String params;
    public String publicPath;
    public LinkedHashMap<String, String> paramsHash;
    public LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();

    public byte[] convertParamsToBytes() {
        return params.getBytes();
    }
}
