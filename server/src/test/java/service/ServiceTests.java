package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import exception.*;
import exception.UserExceptions;
import model.*;
import org.junit.jupiter.api.*;
import passoff.model.*;
import passoff.server.TestServerFacade;
import server.Server;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTests {
/*    Here's what we know.  We need to write a total of 13 tests (might be able to get away with 12) that hit all of the endpoints
We've built.  I don't know what the issue would be if we just copied over the tests from the other file and just changed a few things
but, I think that's probably not the best solution.
Guy is the existingUser, Steve is newUser
*/

    private static UserData guy;
    private static UserData steve;
    private static TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private static Server server;
    private String existingAuth;
    private static GameService gameService;
    private static UserService userService;



    @BeforeAll
    public static void init() {
        MemoryDataAccess memory = new MemoryDataAccess();
        gameService = new GameService(memory);
        userService = new UserService(memory);
        guy = new UserData("Guy", "GuysPassword", "guy@mail.com");
        steve = new UserData("Steve", "StevesPassword", "Steve@mail.com");
    }

    @BeforeEach
    public void setup() throws UserException403, DataAccessException {
        userService.dbclear();
        //one user already logged in

        RegisterResult regiResult = userService.getUser(guy);
        Assertions.assertNotNull(regiResult.authToken());
    }

    @Test
    @Order(1)
    @DisplayName("Proper_GetUser")
    public void registration200() throws UserException403, DataAccessException {

        RegisterResult regiResult = userService.getUser(steve);
        Assertions.assertNotNull(regiResult.authToken());

    }

    @Test
    @Order(2)
    @DisplayName("Improper get User")
    public void registration403(){
        try {
            RegisterResult regiResult = userService.getUser(guy);
            Assertions.assertTrue(false, "Should have failed and didn't.");
        } catch (UserException403 | DataAccessException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Proper Log user")
    public void logIn200() throws UserException401, DataAccessException {

        RegisterResult regiResult = userService.logUser(guy);
        Assertions.assertNotNull(regiResult.authToken());

    }


    @Test
    @Order(4)
    @DisplayName("Improper log user")
    public void logIn401(){
        UserData bubs = new UserData("Guy", "BubsPassword", "bubs@mail.com");
        try {
            RegisterResult regiResult = userService.logUser(bubs);
            Assertions.assertFalse(false, "Should have failed and didn't");
        }
        catch (UserException401 | DataAccessException e) {
            Assertions.assertTrue(true);
        }

    }

    @Test
    @Order(5)
    @DisplayName("Proper_Log_out")
    public void logOut200() throws UserException401, DataAccessException {

        RegisterResult regiResult = userService.logUser(guy);
        String success = userService.logOut(regiResult.authToken());
        Assertions.assertEquals("{}", success);

    }

    @Test
    @Order(6)
    @DisplayName("Improper_Log_out")
    public void logOut401() throws UserException401{
        try {
            String fails = userService.logOut("Totally not an auth token");
            Assertions.assertFalse(false);
        }
        catch (UserException401 | DataAccessException e) {
            Assertions.assertTrue(true);
        }

    }

    @Test
    @Order(7)
    @DisplayName("Proper_List")
    public void list200() throws DataAccessException {

        int gameId = gameService.createGame("Trial");
        GameRetrun games = gameService.returnGames();
        Assertions.assertEquals(1, games.games().size());

    }

    @Test
    @Order(8)
    @DisplayName("Improper_List")
    public void list400() throws DataAccessException {
        userService.dbclear();
        GameRetrun games = gameService.returnGames();
        Assertions.assertEquals(0, games.games().size());
    }

    @Test
    @Order(9)
    @DisplayName("Proper_Create")
    public void create200() throws UserException403, DataAccessException {

        int gameId = gameService.createGame("Trial");
        Assertions.assertEquals(1 ,gameId);

    }

    @Test
    @Order(10)
    @DisplayName("Improper_Create")
    public void create403() throws UserException403, DataAccessException {
//      Similar problem to improper list.
        int gameID = gameService.createGame("");
        Assertions.assertEquals(-1, gameID);

    }

    @Test
    @Order(11)
    @DisplayName("Proper_Join")
    public void join200() throws UserException403, UserExceptions, DataAccessException {
        int gameId = gameService.createGame("Trial");
        RegisterResult regiResult = userService.getUser(steve);
        JoinGameData data = new JoinGameData("White", gameId);
        String success = gameService.joinByColor(data, regiResult.authToken());
        Assertions.assertEquals("{}", success);

    }

    @Test
    @Order(12)
    @DisplayName("Improper_Join")
    public void join400() throws UserException403, UserExceptions{
        try {
            int gameId = gameService.createGame("Trial");
            RegisterResult regiResult = userService.getUser(steve);
            JoinGameData data = new JoinGameData("White", 400);
            String success = gameService.joinByColor(data, regiResult.authToken());
            Assertions.assertEquals("{}", success);
        }
        catch(UserExceptions | DataAccessException e){
            Assertions.assertTrue(true);
        }

    }


    @Test
    @Order(13)
    @DisplayName("Proper authenticate")
    public void authenticate200(){
        try {
            RegisterResult regiResult = userService.getUser(steve);
            boolean authentic = userService.authenticate(regiResult.authToken());
            Assertions.assertEquals(true, authentic);
        }
        catch (UserException403 e){
            Assertions.assertTrue(false, "Something went wrong.");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(14)
    @DisplayName("Improper authenticate")
    public void authenticate401(){
        try {
            RegisterResult regiResult = userService.getUser(steve);
            boolean authentic = userService.authenticate("Totally an auth token");
            Assertions.assertEquals(false, authentic);
        }
        catch (UserException403 | DataAccessException e){
            Assertions.assertTrue(true, "Something went wrong.");
        }
    }

    @Test
    @Order(15)
    @DisplayName("Proper link")
    public void link200() throws DataAccessException {
        UserData bubs = new UserData("Bubs", "bub's password", "bubs@mail.com");
        AuthData authData = userService.linkAuth(bubs);
        Assertions.assertNotNull(authData);
    }

    @Test
    @Order(16)
    @DisplayName("Improper link")
    public void link400() throws DataAccessException {

        UserData bubs = new UserData("", "bub's password", "bubs@mail.com");
        AuthData authData = userService.linkAuth(bubs);
        Assertions.assertNull(authData);

    }

    @Test
    @Order(17)
    @DisplayName("Proper_Clear")
    public void clear200() throws UserException403, DataAccessException {
//      How to write this test?
        RegisterResult regiResult = userService.getUser(steve);
        Assertions.assertNotNull(regiResult.authToken());

    }
}
