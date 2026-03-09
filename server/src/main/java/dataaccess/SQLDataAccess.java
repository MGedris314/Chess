package dataaccess;

import exception.UserException403;
import model.*;

import javax.xml.crypto.Data;
import java.sql.SQLData;

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

    }

    @Override
    public void clearAuth() {

    }

    @Override
    public void clearUsers() {

    }

    @Override
    public void clearGames() {

    }
}
