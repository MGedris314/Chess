package client;

import org.junit.jupiter.api.*;
import server.Server;
import model.*;


public class ServerFacadeTests {

    private static Server server;
    private final String url= "http://localhost:8080";
    private final ServerFacade facade = new ServerFacade(url);

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @BeforeEach
    public void bef(){
        facade.clear("");
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    @Order(1)
    @DisplayName("Add user correct")
    public void addUser200() {
        UserData user = new UserData("Steve", "Steve's secure password", "totally an email.");
        try {
            AuthData correct = facade.addUser(user);
            Assertions.assertNotNull(correct);
        } catch (Exception e) {
            Assertions.assertFalse(true, "Don't panic this one keeps failing because he's already there.");
        }
    }

    @Test
    @Order(2)
    @DisplayName("Add user incorrectly")
    public void addUser400() {
        UserData user = new UserData(null, null, "totally an email.");
        try {
            AuthData correct = facade.addUser(user);
            Assertions.assertNull(correct, "Got here and we shouldn't have.");
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Log in correctly")
    public void logIn200() {
        UserData user = new UserData("Steve", "Steve's secure password", "totally an email.");
        facade.addUser(user);
        try {
            AuthData correct = facade.logI(user);
            Assertions.assertNotNull(correct);
        } catch (Exception e) {
            Assertions.assertTrue(false, "Got here and we shouldn't have.");
        }
    }

    @Test
    @Order(4)
    @DisplayName("Log in incorrectly")
    public void logIn400() {
        UserData user = new UserData("Steve", "Not Steve's password", "totally an email.");
        try {
            AuthData correct = facade.logI(user);
            Assertions.assertNull(correct,"Got here and we shouldn't have.");
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(5)
    @DisplayName("Log out correctly")
    public void logOut200() {
        UserData user = new UserData("Steve", "Steve's secure password", "totally an email.");
        facade.addUser(user);
        AuthData needed = facade.logI(user);
        try{
            String correct = facade.logO(needed.authToken());
            Assertions.assertNull(correct);
        } catch (Exception e) {
            Assertions.fail("Got here and shouldn't have.");
        }
    }

    @Test
    @Order(6)
    @DisplayName("Log out incorrectly")
    public void logOut400() {
        AuthData fake = new AuthData("Totally a user name", "Totally an authenticate token.");
        try{
            String correct = facade.logO(fake.authToken());
            Assertions.assertNotNull(correct, "Got here some how.");
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(7)
    @DisplayName("List games correctly")
    public void listGames200() {
        UserData user = new UserData("Steve", "Steve's secure password", "totally an email.");
        facade.addUser(user);
        AuthData needed = facade.logI(user);
        try{
            GameRetrun games = facade.listGame(needed.authToken());
            Assertions.assertNotNull(games);
        } catch (Exception e) {
            Assertions.assertTrue(false, "Get's here and shouldn't.");
        }
    }

    @Test
    @Order(8)
    @DisplayName("List games incorrectly")
    public void listGames400() {
        AuthData needed = new AuthData("Totally a valid user", "Totally a valid auth token");
        try{
            GameRetrun games = facade.listGame(needed.authToken());
            Assertions.assertNull(games, "Shouldn't get here.");
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(9)
    @DisplayName("Create game correctly")
    public void createGame200() {
        UserData user = new UserData("Steve", "Steve's secure password", "totally an email.");
        facade.addUser(user);
        AuthData needed = facade.logI(user);
        GameName name = new GameName("This is a name");
        try{
            GameData result = facade.createGame(name, needed.authToken());
            Assertions.assertNull(result);
        } catch (Exception e) {
            Assertions.assertTrue(false, "Shouldn't be getting here.");
        }
    }

    @Test
    @Order(10)
    @DisplayName("Create game incorrectly")
    public void createGame400() {
        try{
            GameData result = facade.createGame(null, null);
            Assertions.assertNotNull(result, "Shouldn't get here");
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(11)
    @DisplayName("Join game correctly")
    public void joinGame200() {
        UserData user = new UserData("Steve", "Steve's secure password", "totally an email.");
        facade.addUser(user);
        AuthData needed = facade.logI(user);
        GameName name = new GameName("This is a name");
        facade.createGame(name, needed.authToken());
        JoinGameData joiner = new JoinGameData("black", 1);
        try{
            String check = facade.joinGame(joiner, needed.authToken());
            Assertions.assertNull(check);
        } catch (Exception e) {
            Assertions.assertTrue(false, "Shouldn't get here.");
        }
    }

    @Test
    @Order(12)
    @DisplayName("Join game incorrectly")
    public void joinGame400() {
        try{
            String check = facade.joinGame(null, null);
            Assertions.assertNotNull(check);
        } catch (Exception e) {
            Assertions.assertTrue(true, "Shouldn't get here.");
        }
    }



}
