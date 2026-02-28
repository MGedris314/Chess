package dataaccess;

import exception.UserException403;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface DataAccess {

    UserData findUser(String username);
    UserData addUser(String username, UserData password);
    AuthData addAuthToken(AuthData authToken);
    AuthData findAuth(String authData);
    void removeAuth(String authData);
    int createGame(GameData gameName);
    void createPublic(PublicGame pub);
    int gameID();
    GameRetrun gameReturn();
    GameData returnSingleGame(int gameID) throws UserException403;
    PublicGame editPublic(int gameID);
    void updateGames(GameData game, PublicGame pub, int gameID);
    void clearAuth();
    void clearUsers();
    void clearGames();
}
