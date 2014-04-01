package Routing;

import Request.Request;
import Response.Response;
import Response.ResponseBuilder;
import Util.FileReader;
import Util.FileWriter;
import Util.iFileReader;
import Util.iFileWriter;

import org.apache.commons.codec.digest.DigestUtils;

public class PatchHandler implements RequestHandler {
    private Request request;
    private ResponseBuilder builder;
    private iFileReader fileReader;
    private iFileWriter fileWriter;

    public PatchHandler(Request request) throws Exception {
        this.request = request;
        this.builder = new ResponseBuilder(request);
        this.fileReader = new FileReader(request);
        this.fileWriter = new FileWriter(request);
    }

    public PatchHandler(Request request, iFileReader fileReader, iFileWriter fileWriter) throws Exception {
        this.request = request;
        this.builder = new ResponseBuilder(request);
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
    }

    public Response handle() throws Exception {
        byte[] fileContents = fileReader.read(request.path);
        String fileContentsHex = DigestUtils.sha1Hex(fileContents);
        try {
            if(request.headers.get("If-Match").equals(fileContentsHex)) {
                handleMatchedRequest(fileContents);
            } else {
                builder.buildPreconditionFailedResponse();
            }
        } catch(NullPointerException ex) {
            builder.buildPreconditionFailedResponse();
        }
        return builder.buildFullResponse();
    }

    private void handleMatchedRequest(byte[] fileContents) throws Exception {
        byte[] newFileContents = fileWriter.writePartialContent(fileContents);
        builder.buildPatchResponse(newFileContents);
    }
}
