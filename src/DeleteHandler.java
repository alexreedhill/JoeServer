public class DeleteHandler implements RequestHandler {
    private Request request;
    private ResponseBuilder builder;

    public DeleteHandler(Request request) throws Exception {
        this.request = request;
        builder = new ResponseBuilder(request);
    }

    public Response handle() throws Exception {
        builder.buildOKResponse();
        return builder.buildFullResponse();
    }
}
