package Mocks;

import Request.Request;
import Util.iFileWriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MockFileWriter implements iFileWriter {
    private Request request;
    private File file;
    private byte[] newFileContents;

    public MockFileWriter(Request request) {
        this.request = request;
    }

    public void writeFullContent() throws IOException {
        file = new File(request.publicPath + "/form");
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file);
        String[] fileContents = request.body.split("=");
        writer.write(fileContents[0] + " = " + fileContents[1]);
        writer.close();
    }

    public byte[] writePartialContent(byte[] fileContents) throws Exception {
        int requestContentLength = Integer.parseInt(request.headers.get("Content-Length"));
        checkContentLengths(requestContentLength, fileContents);
        return newFileContents;
    }

    private void checkContentLengths(int requestContentLength, byte[] fileContents) throws UnsupportedEncodingException {
        if(requestContentLength > fileContents.length) {
            System.out.println("Request body: " + request.body);
            newFileContents = request.body.getBytes();
        } else {
            overwriteFileContentsFromBeginningOfFile(fileContents, requestContentLength);
        }
        System.out.println("New file contents: " + new String(fileContents, "UTF-8"));
    }

    private void overwriteFileContentsFromBeginningOfFile( byte[] fileContents, int requestContentLength) {
        newFileContents = new byte[fileContents.length];
        byte[] requestBodyBytes = request.body.getBytes();
        System.arraycopy(requestBodyBytes, 0, newFileContents, 0, requestContentLength);
        System.arraycopy(fileContents, requestContentLength, newFileContents,
                requestContentLength, (fileContents.length - requestContentLength));
    }
}
