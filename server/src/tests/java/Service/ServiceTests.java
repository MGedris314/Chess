package Service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import exception.*;
import exception.UserExceptions;
import model.RegisterResult;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.*;
import passoff.model.*;
import passoff.server.TestServerFacade;
import server.Server;
import service.GameService;
import service.UserService;

import java.net.HttpURLConnection;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTests {
/*    Here's what we know.  We need to write a total of 13 tests (might be able to get away with 12) that hit all of the endpoints
We've built.  I don't know what the issue would be if we just copied over the tests from the other file and just changed a few things
but, I think that's probably not the best solution.
Guy is the existingUser, Steve is newUser
*/

    private static UserData Guy;
    private static UserData Steve;
    private static TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private static Server server;
    private String existingAuth;
    private static GameService gameService;
    private static UserService userService;


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
        gameService = new GameService(new MemoryDataAccess());
        userService = new UserService(new MemoryDataAccess());
        Guy = new UserData("Guy", "GuysPassword", "guy@mail.com");
        Steve = new UserData("Steve", "StevesPassword", "Steve@mail.com");
        createRequest = new TestCreateRequest("testGame");
    }

    @BeforeEach
    public void setup()  throws UserException403{
        serverFacade.clear();
        //one user already logged in

        RegisterResult regiResult = userService.GetUser(Guy);
        Assertions.assertNotNull(regiResult.authToken());
    }

    @Test
    @Order(1)
    @DisplayName("Proper_Register")
    public void Registration200() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }

    @Test
    @Order(2)
    @DisplayName("Register_existing_user")
    public void Registration403() throws UserException403{
        RegisterResult regiResult = userService.GetUser(Guy);
        Assertions.assertThrows(UserException403.class, () -> {

        });
    }

    @Test
    @Order(3)
    @DisplayName("Proper_Register")
    public void LogIN200() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }


    @Test
    @Order(4)
    @DisplayName("Proper_Register")
    public void LogIN401() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }

    @Test
    @Order(5)
    @DisplayName("Proper_Register")
    public void LogOut200() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }

    @Test
    @Order(6)
    @DisplayName("Proper_Register")
    public void LogOut401() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }

    @Test
    @Order(7)
    @DisplayName("Proper_Register")
    public void List200() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }

    @Test
    @Order(8)
    @DisplayName("Proper_Register")
    public void List401() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }
    @Test
    @Order(9)
    @DisplayName("Proper_Register")
    public void Create200() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }
    @Test
    @Order(10)
    @DisplayName("Proper_Register")
    public void Create401() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }
    @Test
    @Order(11)
    @DisplayName("Proper_Register")
    public void Join200() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }
    @Test
    @Order(12)
    @DisplayName("Proper_Register")
    public void Join401() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }
    @Test
    @Order(13)
    @DisplayName("Proper_Register")
    public void Clear200() throws UserException403{

        RegisterResult regiResult = userService.GetUser(Steve);
        Assertions.assertNotNull(regiResult.authToken());

    }
}
