import java.io.IOException;

public class GetHandler implements RequestHandler {
    private Response response;

    public Response handle(Request request) throws IOException {
        response = new Response(request);
        if(response.request.path.equals("/")) {
            response.set200Response();
        } else {
            try {
                response.openResource();
            } catch(IOException ex) {
                response.set404Response();
            }
        }
        return response;
    }
}
