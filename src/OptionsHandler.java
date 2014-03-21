public class OptionsHandler implements RequestHandler {
    private Response response;

    public Response handle(Request request) throws Exception {
        response = new Response(request);
        response.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        response.setStatusCode("200");
        return response;
    }
}
