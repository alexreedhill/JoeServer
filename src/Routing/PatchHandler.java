package Routing;

import Request.Request;
import Response.Response;
import Response.ResponseBuilder;
import Util.FileReader;
import Util.iFileReader;
import org.apache.commons.codec.digest.DigestUtils;

public class PatchHandler implements RequestHandler {
    private Request request;
    private ResponseBuilder builder;
    private iFileReader fileReader;

    public PatchHandler(Request request) throws Exception {
        this.request = request;
        this.builder = new ResponseBuilder(request);
        this.fileReader = new FileReader(request);
    }

    public PatchHandler(Request request, iFileReader fileReader) throws Exception {
        this.request = request;
        this.builder = new ResponseBuilder(request);
        this.fileReader = fileReader;
    }

    public Response handle() throws Exception {
        byte[] fileContents = fileReader.read(request.path);
        String fileContentsHex = DigestUtils.sha1Hex(fileContents);
        try {
            if(request.headers.get("If-Match").equals(fileContentsHex)) {
                builder.buildPatchResponse();
            } else {
                builder.buildPreconditionFailedResponse();
            }
        } catch(NullPointerException ex) {
            builder.buildPreconditionFailedResponse();
        }
        return builder.buildFullResponse();
    }
}
