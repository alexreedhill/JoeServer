import java.io.IOException;

public class GetHandler implements RequestHandler {
    private Response response;
    private FileReader fileReader = new FileReader();

    public Response handle(Request request) throws IOException {
        response = new Response(request);
        if(response.request.path.equals("/")) {
            response.setStatusCode("200");
        } else {
            try {
                openResource();
            } catch(IOException ex) {
                response.setStatusCode("404");
            }
        }
        return response;
    }

    public void openResource() throws IOException {
        response.setStatusCode("200");
        response.setBody(fileReader.read(response.request.path));
    }
}
