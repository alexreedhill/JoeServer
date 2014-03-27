public class GetHandler implements RequestHandler {
    private Request request;
    private ResponseBuilder builder;
    private BasicAuthenticator auth;

    public GetHandler(Request request) throws Exception {
        this.request = request;
        auth = new BasicAuthenticator(request);
        builder = new ResponseBuilder(request);
    }

    public Response handle() throws Exception {
        if(request.path.equals("/") || request.path.equals("/form")) {
            builder.buildDirectoryResponse();
        } else if(restrictedRoute()) {
            builder = auth.authenticate();
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
