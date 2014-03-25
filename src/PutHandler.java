import java.util.ArrayList;

public class PutHandler implements RequestHandler {
    private Request request;
    private ResponseBuilder builder;
    private ArrayList methodNotAllowedUrls = createMethodNotAllowedUrls();

    public PutHandler(Request request) throws Exception {
        this.request = request;
        builder = new ResponseBuilder(request);
    }

    public Response handle() throws Exception {
        if(methodNotAllowed()) {
            builder.buildMethodNotAllowedResponse();
        } else {
            builder.buildOKResponse();
        }
        return builder.buildFullResponse();
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
