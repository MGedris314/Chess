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

}
