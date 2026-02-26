package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

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
    int gameID();
    ArrayList<GameData> gameReturn();
    void clearAuth();
    void clearUsers();
    void clearGames();
}
