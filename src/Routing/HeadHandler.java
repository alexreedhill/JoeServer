package Routing;

import Response.ResponseBuilder;
import Response.Response;
import Request.Request;

public class HeadHandler implements RequestHandler {
    private ResponseBuilder builder;

    public HeadHandler(Request request) throws Exception {
        builder = new ResponseBuilder(request);
    }

    public Response handle()throws Exception {
        builder.buildOKResponse();
        return builder.buildFullResponse();
    }

}
