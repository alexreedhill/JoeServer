package Routing;

import Request.Request;
import Response.Response;

public class Dispatcher {

    public Response dispatch(Request request) throws Exception {
        String method = request.method;
        String classNamePrefix = method.substring(0, 1) + method.substring(1, method.length()).toLowerCase();
        Class<?> handlerClass = Class.forName("Routing." + classNamePrefix + "Handler");
        RequestHandler handler = (RequestHandler)handlerClass.getDeclaredConstructor(Request.class).newInstance(request);
        return handler.handle();
    }
}