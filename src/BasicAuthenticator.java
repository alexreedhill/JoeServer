import java.io.IOException;

public class BasicAuthenticator {
    private Response response;

    public BasicAuthenticator(Response response) {
        this.response = response;
    }

    public Response authenticate() throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=\"JoeServer\"");
        response.setStatusCode("401");
        return response;
    }
}
