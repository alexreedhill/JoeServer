package Routing;

import Request.Request;
import Response.Response;
import Response.ResponseBuilder;
import Util.AppRunner;
import Util.BasicAuthenticator;
import com.google.gson.Gson;

public class GetHandler implements RequestHandler {
    private Request request;
    private ResponseBuilder builder;
    private BasicAuthenticator auth;

    public GetHandler(Request request) throws Exception {
        this.request = request;
        auth = new BasicAuthenticator(request);
        builder = new ResponseBuilder(request);
    }

    public Response handle() throws Exception {
        System.out.println("Request path: " + request.path);
        if(request.path.equals("/")) {
            builder.buildDirectoryResponse();
        } else if(request.path.contains(".json")) {
            Gson gson = new Gson();
            request.json = gson.toJson(request.paramsHash);
            System.out.println("Json: " + request.json);
            AppRunner appRunner = new AppRunner();
            String json = appRunner.run(request);
            builder.buildAppResponse(json);
        } else if(restrictedRoute()) {
            builder = auth.authenticate();
        } else if(request.path.equals("/parameters")) {
            builder.buildParameterResponse();
        } else {
            builder.buildFileResponse();
        }
        return builder.buildFullResponse();
    }

    public Boolean restrictedRoute() {
        return request.path.equals("/logs");
    }
}
