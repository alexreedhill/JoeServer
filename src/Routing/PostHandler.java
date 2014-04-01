package Routing;

import Request.Request;
import Response.Response;
import Response.ResponseBuilder;
import Util.FileWriter;

import java.util.ArrayList;

public class PostHandler implements RequestHandler {
    private Request request;
    private ResponseBuilder builder;
    private FileWriter fileWriter;

    public PostHandler(Request request) throws Exception {
        this.request = request;
        builder = new ResponseBuilder(request);
        fileWriter = new FileWriter(request);
    }

    public Response handle() throws Exception {
        if(request.path.equals("/form")) {
            builder.buildOKResponse();
            fileWriter.write();
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
            add("/text-file.txt");
        }};
    }
}
