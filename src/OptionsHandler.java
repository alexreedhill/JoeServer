import java.io.IOException;

public class OptionsHandler implements RequestHandler {
    private Response response;

    public OptionsHandler(Request request) throws IOException {
        response = new Response(request);
    }

    public Response handle()throws IOException {
        response.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        response.statusCode = "200";
        return response;
    }
}
