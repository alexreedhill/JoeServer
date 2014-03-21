import java.io.IOException;

public class PostHandler implements RequestHandler {
    private Response response;

    public Response handle(Request request) throws IOException {
        response = new Response(request);
        response.setStatusCode("200");
        return response;
    }
}
