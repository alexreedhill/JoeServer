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
        if(request.path.equals("/")) {
            response.statusCode = "200";
        } else if(restrictedRoute()) {
            response = auth.authenticate();
        } else if(request.path.equals("/parameters")) {
            response.statusCode = "200";
            response.body = (request.convertParamsToString()).getBytes();
        } else {
            try {
                openResource();
            } catch(IOException ex) {
                response.statusCode = "404";
            }
        }
        return response;
    }

    private void openResource() throws IOException {
        response.statusCode = "200";
        response.body = fileReader.read(request.path);
    }

    public Boolean restrictedRoute() {
        return request.path.equals("/logs");
    }
}
