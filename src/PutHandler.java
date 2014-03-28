import java.util.ArrayList;

public class PutHandler implements RequestHandler {
    private Request request;
    private ResponseBuilder builder;

    public PutHandler(Request request) throws Exception {
        this.request = request;
        builder = new ResponseBuilder(request);
    }

    public Response handle() throws Exception {
        if(request.path.equals("/form")) {
            FileWriter writer = new FileWriter(request, builder);
            writer.write();
            builder.buildOKResponse();
        } else if(methodNotAllowed()) {
            builder.buildMethodNotAllowedResponse();
        } else {
            builder.buildOKResponse();
        }
        return builder.buildFullResponse();
    }

    private boolean methodNotAllowed() {
        ArrayList methodNotAllowedUrls = createMethodNotAllowedUrls();
        return methodNotAllowedUrls.contains(request.path);
    }

    private ArrayList createMethodNotAllowedUrls() {
        return new ArrayList<String>() {{
            add("/file1");
        }};
    }


}
