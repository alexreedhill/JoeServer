package Util;

import Request.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppRunner {
    private String input;
    private String error;

    public String run(Request request) throws Exception  {
        Process app = Runtime.getRuntime().exec("java -jar ttt-rb.jar " + request.json);
        readInput(app);
        System.err.println(error);
        return input;
    }

    private void readInput(Process app) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(app.getInputStream()));
        BufferedReader err = new BufferedReader(new InputStreamReader(app.getErrorStream()));
        input = in.readLine();
        error = err.readLine();
    }
}
