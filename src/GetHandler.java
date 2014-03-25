import java.io.IOException;

public class GetHandler implements RequestHandler {
    private Request request;
    private Response response;
    private FileReader fileReader;
    private BasicAuthenticator auth;
    private String rangeHeader;


    public GetHandler(Request request) throws Exception {
        this.request = request;
        this.response = new Response(request);
        fileReader = new FileReader();
        auth = new BasicAuthenticator(request, response);
    }

    public Response handle() throws Exception {
        if(request.path.equals("/")) {
            response.statusCode = "200";
        } else if(restrictedRoute()) {
            response = auth.authenticate();
        } else if(request.path.equals("/parameters")) {
            response.statusCode = "200";
            response.body = request.convertParamsToBytes();
        } else {
            try {
                openResource();
                if((rangeHeader = request.headers.get("Range")) != null ) {
                    partialContentResponse();
                } else {
                    response.statusCode = "200";
                }
            } catch(IOException ex) {
                response.statusCode = "404";
            }
        }
        return response;
    }

    private void partialContentResponse() throws Exception {
        response.statusCode = "206";
        String[] splitRangeHeader = rangeHeader.split("-");
        int start = Integer.parseInt(splitRangeHeader[0].replace("bytes=", ""));
        int length = Integer.parseInt(splitRangeHeader[1]);
        response.setHeader("Content-Range", "bytes " + start + "-" + length + "/" + response.body.length);
        byte[] partialContent = new byte[length];
        System.arraycopy(response.body, start, partialContent, 0, length);
        response.body = partialContent;
    }

    private void openResource() throws IOException {
        response.body = fileReader.read(request.path);
    }

    public Boolean restrictedRoute() {
        return request.path.equals("/logs");
    }
}
