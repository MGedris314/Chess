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
}
