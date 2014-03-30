import java.io.IOException;
import java.util.ArrayList;

public class RequestValidator {

    protected boolean validate(String httpRequest) throws IOException {
        ArrayList<String> invalidRequests = createInvalidRequests();
        return !invalidRequests.contains(httpRequest);
    }

    private ArrayList<String> createInvalidRequests() {
        return new ArrayList<String>() {{
            add("");
            add(null);
        }};
    }
}
