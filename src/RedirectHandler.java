
public class RedirectHandler {
    private ResponseBuilder builder;

    public RedirectHandler(Request request) throws Exception {
        builder = new ResponseBuilder(request);
    }

    public Response handle() throws Exception {
        builder.buildRedirectResponse();
        return builder.buildFullResponse();
    }
}
