package Util;

import Request.Request;

import java.io.*;

public class FileWriter implements Util.iFileWriter {
    private Request request;
    private File file;
    private byte[] newFileContents;

    public FileWriter(Request request) {
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
        writeWithFileOutputStream();
        return newFileContents;
    }

    private void checkContentLengths(int requestContentLength, byte[] fileContents) throws UnsupportedEncodingException {
        if(requestContentLength > fileContents.length) {
            newFileContents = request.body.getBytes();
        } else {
            overwriteFileContentsFromBeginningOfFile(fileContents, requestContentLength);
        }
`    }

    private void overwriteFileContentsFromBeginningOfFile( byte[] fileContents, int requestContentLength) {
        newFileContents = new byte[fileContents.length];
        byte[] requestBodyBytes = request.body.getBytes();
        System.arraycopy(requestBodyBytes, 0, newFileContents, 0, requestContentLength);
        System.arraycopy(fileContents, requestContentLength, newFileContents,
                requestContentLength, (fileContents.length - requestContentLength));
    }

    private void writeWithFileOutputStream() throws Exception {
        file = new File(request.publicPath + request.path);
        FileOutputStream writer = new FileOutputStream(file);
        writer.write(newFileContents);
        writer.close();
    }
}
