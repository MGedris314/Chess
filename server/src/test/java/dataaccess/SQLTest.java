package dataaccess;
import model.*;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SQLTest {
    private static SQLDataAccess dataAccess;


    @BeforeAll
    public static void init(){
        dataAccess = new SQLDataAccess();
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        dataAccess.clearAuth();
        dataAccess.clearGames();
        dataAccess.clearUsers();
    }

    @Test
    @Order(1)
    @DisplayName("Proper add")
    public void add200() throws DataAccessException {
        UserData userData = new UserData("Steve", "red", "mail@.mail");
        UserData check = dataAccess.addUser("Steve", userData);
        Assertions.assertNull(check);
    }

    @Test
    @Order(2)
    @DisplayName("Improper add")
    public void add500() throws DataAccessException {
        UserData userData = new UserData("Steve", "", "Mail@mail");
        UserData nulled = null;
        try{
            dataAccess.addUser("Gary", nulled);
            Assertions.assertTrue(false, "Shouldn't get here.");
        }
        catch (DataAccessException e){
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Proper find")
    public void find200() throws DataAccessException {
        UserData userData = new UserData("Steve", "red", "mail@.mail");
        UserData check = dataAccess.addUser("Steve", userData);
        UserData found = dataAccess.findUser("Steve");
        Assertions.assertEquals("Steve", found.username());
    }

    @Test
    @Order(4)
    @DisplayName("Improper find")
    public void find500() throws DataAccessException {
        UserData userData = new UserData("Steve", "red", "mail@.mail");
        UserData check = dataAccess.addUser("Steve", userData);
        UserData found = dataAccess.findUser("Gary");
        Assertions.assertNull(found);
    }

    @Test
    @Order(5)
    @DisplayName("proper add auth")
    public void atuth200() throws DataAccessException {
        AuthData auth = new AuthData("Phill", "2");
        AuthData check = dataAccess.addAuthToken(auth);
        Assertions.assertEquals(auth, check);
    }

    @Test
    @Order(6)
    @DisplayName("Improper add auth")
    public void atuth500() throws DataAccessException {
        AuthData auth = null;
        try {
            AuthData check = dataAccess.addAuthToken(auth);
            Assertions.assertNull(check);
        }
        catch (DataAccessException e ){
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(7)
    @DisplayName("proper find auth")
    public void atuthFind200() throws DataAccessException {
        AuthData auth = new AuthData("Phill", "2");
        AuthData check = dataAccess.addAuthToken(auth);
        AuthData found = dataAccess.findAuth(check.userName());
        AuthData rewire = new AuthData(found.authToken(), found.userName());
        Assertions.assertEquals(check, rewire);
    }

    @Test
    @Order(8)
    @DisplayName("improper find auth")
    public void atuthFind500() throws DataAccessException {
        AuthData auth = new AuthData("Phill", "2");
        dataAccess.addAuthToken(auth);
        AuthData found = dataAccess.findAuth("Totally an auth token");
        Assertions.assertNull(found);
    }

    @Test
    @Order(9)
    @DisplayName("proper remove auth")
    public void atuthremove200() throws DataAccessException {
        AuthData auth = new AuthData("Phill", "2");
        dataAccess.addAuthToken(auth);
        dataAccess.removeAuth("2");
        Assertions.assertTrue(true);
    }

    @Test
    @Order(10)
    @DisplayName("Improper remove auth")
    public void atuthremove500() throws DataAccessException {
        AuthData auth = new AuthData("Phill", "2");
        dataAccess.addAuthToken(auth);
        try {
            dataAccess.removeAuth("Completely valid auth token");
            Assertions.assertTrue(true);
        }
        catch (DataAccessException e){
            Assertions.assertTrue(true);
        }
    }
}
