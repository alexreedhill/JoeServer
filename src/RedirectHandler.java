import java.io.IOException;

public class RedirectHandler {
    private Response response;

    public RedirectHandler(Request request) throws IOException {
        response = new Response(request);
    }

    public Response handle() throws Exception {
        response.setRedirectResponse();
        return response;
    }
}
