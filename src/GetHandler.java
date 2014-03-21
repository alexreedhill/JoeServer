import java.io.IOException;

public class GetHandler implements RequestHandler {
    private Response response;
    private FileReader fileReader = new FileReader();

    public Response handle(Request request) throws IOException {
        response = new Response(request);
        if(response.request.path.equals("/")) {
            response.set200Response();
        } else {
            try {
                openResource();
            } catch(IOException ex) {
                response.set404Response();
            }
        }
        return response;
    }

    public void openResource() throws IOException {
        response.set200Response();
        response.setBody(fileReader.read(response.request.path));
    }
}
