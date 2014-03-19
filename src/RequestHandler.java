import java.io.IOException;

/**
 * Created by alexhill on 3/19/14.
 */
public interface RequestHandler {
    public void handle(Response response) throws IOException;
}
