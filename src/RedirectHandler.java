public class RedirectHandler {
    private Response response;

    public Response handle(Request request) throws Exception {
        response = new Response(request);
        response.set301Response();
        return response;
    }
}
