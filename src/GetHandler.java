import java.io.IOException;

public class GetHandler implements RequestHandler {
    private Request request;
    private Response response;
    private FileReader fileReader = new FileReader();

    public GetHandler(Request request) throws IOException {
        this.request = request;
        response = new Response(request);
    }

    public Response handle() throws IOException {
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
        response.setBody(fileReader.read(request.path));
    }

    public Boolean restrictedRoute() {
        return request.path.equals("/logs");
    }
}
