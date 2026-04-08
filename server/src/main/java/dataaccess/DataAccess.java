package dataaccess;

import exception.UserException403;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface DataAccess {

    UserData findUser(String username) throws DataAccessException;
    UserData addUser(String username, UserData password) throws DataAccessException;
    AuthData addAuthToken(AuthData authToken) throws DataAccessException;
    AuthData findAuth(String authData) throws DataAccessException;
    void removeAuth(String authData) throws DataAccessException;
    int createGame(GameData gameName) throws DataAccessException;
    void createPublic(PublicGame pub) throws DataAccessException;
    int gameID() throws DataAccessException;
    GameRetrun gameReturn() throws DataAccessException;
    GameData returnSingleGame(int gameID) throws UserException403, DataAccessException;
    PublicGame editPublic(int gameID) throws DataAccessException;

    void Hello_there(GameData game, int gameID) throws DataAccessException;

    void updateGames(GameData game, PublicGame pub, int gameID) throws DataAccessException;
    void clearAuth() throws DataAccessException;
    void clearUsers() throws DataAccessException;
    void clearGames() throws DataAccessException;
}
