import java.io.IOException;

/**
 * Created by alexhill on 3/19/14.
 */
public class PostHandler implements RequestHandler {
    public void handle(Response response) throws IOException {
        response.set200Response();
    }
}
