import java.io.IOException;

public class PostHandler implements RequestHandler {
    private Response response;

    public PostHandler(Request request) throws Exception {
        response = new Response(request);
    }

    public Response handle() throws IOException {
        response.setStatusCode("200");
        return response;
    }
}
