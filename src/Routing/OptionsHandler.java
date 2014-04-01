package Routing;

import Response.ResponseBuilder;
import Response.Response;
import Request.Request;

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
