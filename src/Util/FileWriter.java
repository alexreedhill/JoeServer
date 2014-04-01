package Util;

import Request.Request;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {
    private Request request;
    private File file;

    public FileWriter(Request request) {
        this.request = request;
        file = new File(request.publicPath + "/form");
    }

    public void write() throws IOException {
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file);
        String[] fileContents = request.body.split("=");
        writer.write(fileContents[0] + " = " + fileContents[1]);
        writer.close();
    }
}
