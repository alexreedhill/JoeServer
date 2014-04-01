package Routing;

import Response.Response;

public interface RequestHandler {
    public Response handle() throws Exception;
}
