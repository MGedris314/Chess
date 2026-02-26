package dataaccess;

import model.GameData;
import model.UserData;
import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryDataAccess implements DataAccess {
    final private HashMap<String, UserData> userDataHasMap = new HashMap<>();
    final private HashMap<String, AuthData> authDataHashMap = new HashMap<>();
    final private ArrayList<GameData> games = new ArrayList<GameData>();


    @Override
    public UserData findUser(String username){
        return userDataHasMap.get(username);
    }

    public UserData addUser(String username, UserData password){
        userDataHasMap.put(username, password);
        return null;
    }

    @Override
    public AuthData addAuthToken(AuthData authToken) {
        authDataHashMap.put(authToken.userName(), authToken);
        return null;
    }

    @Override
    public AuthData findAuth(String authData) {
        if(authDataHashMap.containsKey(authData)){
            return authDataHashMap.get(authData);
        }
        else{
            return null;
        }
    }

    @Override
    public void removeAuth(String authData) {
        authDataHashMap.remove(authData);
    }

    @Override
    public int createGame(GameData gameName) {
        games.add(gameName);
        return gameName.gameID();
    }

    @Override
    public int gameID() {
        return games.size();
    }

    @Override
    public ArrayList<GameData> gameReturn() {
        return games;
    }

}
