import java.io.IOException;

/**
 * Created by alexhill on 3/19/14.
 */
public class GetHandler implements RequestHandler {

    public void handle(Response response) throws IOException {
        if(response.request.path.equals("/")) {
            response.set200Response();
        } else {
            try {
                response.openResource();
            } catch(IOException ex) {
                response.set404Response();
            }
        }
    }
}
