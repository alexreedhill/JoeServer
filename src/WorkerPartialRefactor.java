//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//
///**
// * Created by alexhill on 3/28/14.
// */
//public class WorkerPartialRefactor {
//        private Socket clientSocket;
//        private OutputStream clientOutputStream;
//        private BufferedReader in;
//        private String publicPath;
//        private String httpRequest;
//        private int bodyContentLength = 0;
//
//        public WorkerPartialRefactor(Socket clientSocket, String publicPath) {
//            this.clientSocket = clientSocket;
//            this.publicPath = publicPath;
//        }
//
//        public void run() {
//            try {
//                setupStreams();
//                listenForRequest();
//                if (validRequest(httpRequest) ) {
//                    Request request = new RequestBuilder(httpRequest, publicPath).build();
//                    serveResponse(createResponse(request));
//                }
//                clientSocket.close();
//            } catch (Exception ex) {
//                System.err.println(ex);
//            }
//        }
//
//        private void setupStreams() throws IOException {
//            clientOutputStream = clientSocket.getOutputStream();
//            InputStream clientInputStream = clientSocket.getInputStream();
//            in = new BufferedReader(new InputStreamReader(clientInputStream));
//        }
//
//        private void listenForRequest() throws IOException {
//            String httpRequestLine = in.readLine();
//            if(validRequest(httpRequestLine)) {
//                readRequest(httpRequestLine);
//            }
//        }
//
//        private void readRequest(String httpRequestLine) throws IOException {
//            httpRequest += httpRequestLine + "\r\n";
//            char[] requestBuffer = new char[0];
//            while(!requestComplete(httpRequestLine)) {
//                requestBuffer = readRequestLine(requestBuffer);
//            }
//            readBody(requestBuffer);
//        }
//
//        private char[] readRequestLine(char[] requestBodyBuffer) throws IOException {
//            String httpRequestLine = in.readLine();
//            httpRequest += httpRequestLine + "\n";
//            if(httpRequestLine.contains("Content-Length")) {
//                int bodyContentLength = Integer.parseInt(httpRequestLine.split(": ")[1]);
//                requestBodyBuffer = new char[bodyContentLength];
//            }
//            return requestBodyBuffer;
//        }
//
//        private void readBody(char[] requestBuffer) throws IOException {
//            in.read(requestBuffer, 0, bodyContentLength);
//            httpRequest += new String(requestBuffer);
//        }
//
//        private boolean validRequest(String httpRequest) throws IOException {
//            ArrayList<String> invalidRequests = createInvalidRequests();
//            return !invalidRequests.contains(httpRequest);
//        }
//
//        private ArrayList<String> createInvalidRequests() {
//            return new ArrayList<String>() {{
//                add("");
//                add(null);
//            }};
//        }
//
//        private byte[] createResponse(Request request) throws Exception {
//            Dispatcher dispatcher = new Dispatcher();
//            Response response = dispatcher.dispatch(request);
//            System.out.println("Full response: " + new String(response.fullResponse, "UTF-8"));
//            return response.fullResponse;
//        }
//
//        private void serveResponse(byte[] fullResponse) throws IOException {
//            DataOutputStream writer = new DataOutputStream(clientOutputStream);
//            writer.write(fullResponse, 0, fullResponse.length);
//            writer.flush();
//            writer.close();
//        }
//
//        private boolean requestComplete(String httpRequestLine) throws IOException {
//            return httpRequestLine == null || httpRequestLine.equals("") || httpRequestLine.contains("\r\n");
//        }
//    }
//
//}
