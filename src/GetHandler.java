import java.io.IOException;

public class GetHandler implements RequestHandler {
    private Request request;
    private ResponseBuilder builder;

    public GetHandler(Request request) throws Exception {
        this.request = request;
        builder = new ResponseBuilder(request);
    }

    public Response handle() throws Exception {
        if(request.path.equals("/")) {
            builder.buildOKResponse();
        } else if(restrictedRoute()) {
            builder.buildAuthenticatedResponse();
        } else if(request.path.equals("/parameters")) {
            builder.buildParameterResponse();
        } else {
            builder.buildFileResponse();
        }
        return builder.buildFullResponse();
    }

    public Boolean restrictedRoute() {
        return request.path.equals("/logs");
    }
}
