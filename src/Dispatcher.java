import java.io.IOException;
import java.lang.reflect.Method;

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
        Class<?> handlerClass = Class.forName(classNamePrefix + "Handler");
        Object handlerClassInstance = handlerClass.getDeclaredConstructor(Request.class).newInstance(request);
        Method handleMethod = handlerClass.getMethod("handle");
        response = (Response)handleMethod.invoke(handlerClassInstance);
    }
}