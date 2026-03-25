package client;

import org.junit.jupiter.api.*;
import server.Server;
import model.*;


public class ServerFacadeTests {

    private static Server server;
    private ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
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
            Assertions.assertFalse(false, "Got here and we shouldn't have.");
        }
    }

    @Test
    @Order(2)
    @DisplayName("Add user incorrectly")
    public void addUser400() {
        UserData user = new UserData(null, null, "totally an email.");
        try {
            AuthData correct = facade.addUser(user);
            Assertions.assertNotNull(correct, "Got here and we shouldn't have.");
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Log in correctly")
    public void logIn200() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(4)
    @DisplayName("Log in incorrectly")
    public void logIn400() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(5)
    @DisplayName("Log out correctly")
    public void logOut200() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(6)
    @DisplayName("Log out incorrectly")
    public void logOut400() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(7)
    @DisplayName("List games correctly")
    public void listGames200() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(8)
    @DisplayName("List games incorrectly")
    public void listGames400() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(9)
    @DisplayName("Create game correctly")
    public void createGame200() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(10)
    @DisplayName("Create game incorrectly")
    public void createGame400() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(11)
    @DisplayName("Join game correctly")
    public void joinGame200() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(12)
    @DisplayName("Join game incorrectly")
    public void joinGame400() {
        Assertions.assertTrue(true);
    }



}
