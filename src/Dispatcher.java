import java.lang.reflect.Method;

public class Dispatcher {

    public Response dispatch(Request request) throws Exception {
        String method = request.method;
        String classNamePrefix = method.substring(0, 1) + method.substring(1, method.length()).toLowerCase();
        Class<?> handlerClass = Class.forName(classNamePrefix + "Handler");
        Method handleMethod = handlerClass.getMethod("handle", Request.class);
        return (Response)handleMethod.invoke(handlerClass.newInstance(), request);
    }
}
