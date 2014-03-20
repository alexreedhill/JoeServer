import java.io.IOException;
import java.lang.reflect.Method;

public class Dispatcher {
    private Response response;

    public Response dispatch(Request request) throws IOException {
        String method = request.method;
        String classNamePrefix = method.substring(0, 1) + method.substring(1, method.length()).toLowerCase();
        try {
            Class handlerClass = Class.forName(classNamePrefix + "Handler");
            Method handleMethod = handlerClass.getMethod("handle", Request.class);
            response = (Response)handleMethod.invoke(handlerClass.newInstance(), request);
        } catch(Exception ex) {
            System.out.println(ex);
        }
        return response;
    }
}
