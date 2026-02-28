package Service;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import passoff.model.*;
import passoff.server.TestServerFacade;
import server.Server;

import java.net.HttpURLConnection;
import java.util.*;


public class ServiceTests {
/*    Here's what we know.  We need to write a total of 13 tests (might be able to get away with 12) that hit all of the endpoints
We've built.  I don't know what the issue would be if we just copied over the tests from the other file and just changed a few things
but, I think that's probably not the best solution.
Guy is the existingUser, Steve is newUser
*/

    private static TestUser Guy;
    private static TestUser Steve;
    private static TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private static Server server;
    private String existingAuth;

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new TestServerFacade("localhost", Integer.toString(port));
        Guy = new TestUser("Guy", "GuysPassword", "guy@mail.com");
        Steve = new TestUser("Steve", "StevesPassword", "Steve@mail.com");
        createRequest = new TestCreateRequest("testGame");
    }

    @BeforeEach
    public void setup() {
        serverFacade.clear();
        //one user already logged in
        TestAuthResult regResult = serverFacade.register(Guy);
        existingAuth = regResult.getAuthToken();
    }

}
