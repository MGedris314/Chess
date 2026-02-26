package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

public interface DataAccess {

    UserData findUser(String username);
    UserData addUser(String username, UserData password);
    AuthData addAuthToken(AuthData authToken);
    AuthData findAuth(String authData);
    void removeAuth(String authData);
    int createGame(GameData gameName);
    int gameID();
}
