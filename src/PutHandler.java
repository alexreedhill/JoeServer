import java.util.ArrayList;

public class PutHandler implements RequestHandler {
    private Request request;
    private Response response;
    private ArrayList methodNotAllowedUrls = createMethodNotAllowedUrls();

    public PutHandler(Request request) throws Exception {
        this.request = request;
        response = new Response(request);
    }

    public Response handle() {
        if(methodNotAllowed()) {
            response.statusCode = "405";
        } else {
            response.statusCode = "200";
        }
        return response;
    }

    private boolean methodNotAllowed() {
        return methodNotAllowedUrls.contains(request.path);
    }

    private ArrayList createMethodNotAllowedUrls() {
        return new ArrayList<String>() {{
            add("/file1");
        }};
    }


}
