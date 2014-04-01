package Request;

import java.io.IOException;
import java.util.ArrayList;

public class RequestValidator {
    private final ArrayList<String> invalidRequests = createInvalidRequests();

    public boolean validate(String httpRequest) throws IOException {
        return !invalidRequests.contains(httpRequest);
    }

    private ArrayList<String> createInvalidRequests() {
        return new ArrayList<String>() {{
            add("");
            add(null);
        }};
    }
}
