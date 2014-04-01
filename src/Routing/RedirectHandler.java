package Routing;

import Response.ResponseBuilder;
import Response.Response;
import Request.Request;

public class RedirectHandler implements RequestHandler {
    private ResponseBuilder builder;

    public RedirectHandler(Request request) throws Exception {
        builder = new ResponseBuilder(request);
    }

    public Response handle() throws Exception {
        builder.buildRedirectResponse();
        return builder.buildFullResponse();
    }
}
