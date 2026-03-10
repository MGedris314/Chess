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
    public UserData findUser(String username) {
        return null;
    }

    @Override
    public UserData addUser(String username, UserData password) {
        return null;
    }

    @Override
    public AuthData addAuthToken(AuthData authToken) {
        return null;
    }

    @Override
    public AuthData findAuth(String authData) {
        return null;
    }

    @Override
    public void removeAuth(String authData) {

    }

    @Override
    public int createGame(GameData gameName) {
        return 0;
    }

    @Override
    public void createPublic(PublicGame pub) {

    }

    @Override
    public int gameID() {
        return 0;
    }

    @Override
    public GameRetrun gameReturn() {
        return null;
    }

    @Override
    public GameData returnSingleGame(int gameID) throws UserException403 {
        return null;
    }

    @Override
    public PublicGame editPublic(int gameID) {
        return null;
    }

    @Override
    public void updateGames(GameData game, PublicGame pub, int gameID) {
        var statement1 = "UPDATE games SET whiteTeam = game.whiteUsername() blackTeam = game.blackUsername() WHERE id = gameID";
        var statement2 = "UPDATE public SET whiteTeam = pub.whiteUsername() blackTeam = pub.blackUsername() WHERE id = gameID";
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
            `email` varchar(256) NOT NULL,
            `token` varchar(128) NOT NULL,
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
