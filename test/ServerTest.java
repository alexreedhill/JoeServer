import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.*;


/**
 * Created by alexhill on 3/17/14.
 */
public class ServerTest {

    @Test public void basicGetRequestReturnsStatus200() throws IOException {
      Server server = new Server();
      MockClient client = new MockClient();
      server.run();
      String statusCode = client.requestResponseStatusCode("GET", "/");
      assertEquals(statusCode, "200");
    }

    @Test
}
