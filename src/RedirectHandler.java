import java.io.IOException;

public class RedirectHandler {
    private Response response;

    public RedirectHandler(Request request) throws IOException {
        response = new Response(request);
    }

    public Response handle() throws Exception {
        setRedirectResponse();
        return response;
    }

    private void setRedirectResponse() {
        response.statusCode = "307";
        response.setHeader("Location", "http://localhost:5000/");
    }
}
