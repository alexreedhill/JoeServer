import java.io.IOException;

/**
 * Created by alexhill on 3/19/14.
 */
public interface RequestHandler {
    public Response handle(Request request) throws Exception;
}
