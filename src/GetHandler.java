import java.io.IOException;

public class GetHandler implements RequestHandler {
    private Request request;
    private Response response;
    private FileReader fileReader;
    private BasicAuthenticator auth;

    public GetHandler(Request request) throws IOException {
        this.request = request;
        response = new Response(request);
        fileReader = new FileReader();
        auth = new BasicAuthenticator(response);
    }

    public Response handle() throws IOException {
        if(response.request.path.equals("/")) {
            response.statusCode = "200";
        } else {
            if(restrictedRoute()) {
                response = auth.authenticate();
            }
            try {
                openResource();
            } catch(IOException ex) {
                response.statusCode = "404";
            }
        }
        return response;
    }

    public void openResource() throws IOException {
        response.statusCode = "200";
        response.body = fileReader.read(request.path);
    }

    public Boolean restrictedRoute() {
        return request.path.equals("/logs");
    }
}
