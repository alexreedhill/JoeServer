public class OptionsHandler implements RequestHandler {
    private ResponseBuilder builder;

    public OptionsHandler(Request request) throws Exception {
        builder = new ResponseBuilder(request);
    }

    public Response handle()throws Exception {
        builder.buildOptionsResponse();
        return builder.buildFullResponse();
    }
}
