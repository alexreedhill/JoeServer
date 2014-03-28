import java.io.IOException;
import java.util.ArrayList;

public class RequestValidator {
    private ArrayList<String> invalidRequests = createInvalidRequests();

    protected boolean validate(String httpRequest) throws IOException {
        return !invalidRequests.contains(httpRequest);
    }

    private ArrayList<String> createInvalidRequests() {
        return new ArrayList<String>() {{
            add("");
            add(null);
        }};
    }
}
