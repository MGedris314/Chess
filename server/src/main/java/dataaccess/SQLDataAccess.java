package dataaccess;

import exception.UserException403;
import model.*;

import javax.xml.crypto.Data;
import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;;

public class SQLDataAccess implements DataAccess {



    public SQLDataAccess() {
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            System.out.println("Got the error yay!");
        }
    }

    @Override
    public UserData findUser(String username) throws DataAccessException{
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "SELECT * FROM users WHERE name = username")) {
                try(var rs = statement.executeQuery()){
                    rs.next();
                    var name = rs.getString("name");
                    if(name.isEmpty()){
                        return null;
                    }
                    else {
                        UserData user = new UserData(name, null, null);
                        return user;
                    }
                }

            }
        }
        catch (DataAccessException e){
            throw new DataAccessException("e");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserData addUser(String username, UserData password) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "INSERT INTO users (name, password) VALUES (?, ?)")) {
                try(var rs = statement.executeQuery()){
                    statement.setString(1, username);
                    statement.setString(2, password.password());
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthData addAuthToken(AuthData authToken) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "INSERT INTO users (token) VALUES (authToken) WHERE (name = username)")){
                try(var rs = statement.executeQuery()){
                    String username = authToken.userName();
                    statement.setString(2, username);
                    statement.setString(1, authToken.authToken());
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthData findAuth(String authData) {
        var statement = "SELECT * FROM users WHERE token = authData";
        return null;
    }

    @Override
    public void removeAuth(String authData) {
        var statement = "UPDATE users SET token = NULL WHERE token = authData";
    }

    @Override
    public int createGame(GameData gameName) {
        var statement = "INSERT INTO public (id, name, whiteTeam, blackTeam) VALUES(?, ?, null, null, ?";
        return 0;
    }

    @Override
    public void createPublic(PublicGame pub) {
        var statement = "INSERT INTO public (id, name, whiteTeam, blackTeam) VALUES(?, ?, null, null, ?";
    }

    @Override
    public int gameID() {
        return 0;
    }

    @Override
    public GameRetrun gameReturn() {
        var statement = "SELECT * FROM public";
        return null;
    }

    @Override
    public GameData returnSingleGame(int gameID) throws UserException403 {
        var statement = "SELECT * FROM games WHERE id = gameID";
        return null;
    }

    @Override
    public PublicGame editPublic(int gameID) {
        var statement = "SELECT * FROM public WHERE id = gameID";
        return null;
    }

    @Override
    public void updateGames(GameData game, PublicGame pub, int gameID) {
        var statement1 = "UPDATE games SET whiteTeam = ? blackTeam = ? WHERE id = gameID";
        var statement2 = "UPDATE public SET whiteTeam = ? blackTeam = ? WHERE id = gameID";
    }

    @Override
    public void clearAuth() {
        var statement = "DROP COLUMN token";
    }

    @Override
    public void clearUsers() {
        var statement = "DELETE FROM users WHERE id=?";
    }

    @Override
    public void clearGames() {
        var statement = "DELETE FROM games WHERE id=?";
    }

    private final String [] createUserStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users(
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(256) NOT NULL,
            `password` varchar(256) NOT NULL,
            `token` varchar(128),
            PRIMARY KEY (`id`)
            )
            """
    };

    private final String [] createGameStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games(
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(256) NOT NULL,
            `whiteTeam` TEXT DEFAULT NULL,
            `blackTeam` TEXT DEFAULT NULL,
            `game` nvarchar(max)
             PRIMARY KEY (`id`)
            )
            """
    };

    private final String [] createPublicStatements = {
            """
            CREATE TABLE IF NOT EXISTS  public(
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(256) NOT NULL,
            `whiteTeam` TEXT DEFAULT NULL,
            `blackTeam` TEXT DEFAULT NULL,
             PRIMARY KEY (`id`)
            )
            """
    };

}
