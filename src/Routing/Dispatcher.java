package Routing;

import Request.Request;
import Response.Response;

import java.io.IOException;

public class Dispatcher {
    private Response response;

    public Response dispatch(Request request) throws IOException {
        try {
            handleRequestBasedOnMethod(request);
        } catch(Exception ex) {
            System.out.println(ex);
        }
        return response;
    }

    private void handleRequestBasedOnMethod(Request request) throws Exception {
        String method = request.method;
        String classNamePrefix = method.substring(0, 1) + method.substring(1, method.length()).toLowerCase();
        Class<?> handlerClass = Class.forName("Routing." + classNamePrefix + "Handler");
        RequestHandler handler = (RequestHandler)handlerClass.getDeclaredConstructor(Request.class).newInstance(request);
        response = handler.handle();
    }
}