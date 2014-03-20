/**
 * Created by alexhill on 3/20/14.
 */
public class OptionsHandler implements RequestHandler {
    private Request request;
    private Response response;

    public Response handle(Request request) throws Exception {
        response = new Response(request);
        response.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        response.set200Response();
        return response;
    }
}
