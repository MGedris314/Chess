package dataaccess;

import model.GameData;
import model.UserData;
import model.AuthData;
import model.PublicGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryDataAccess implements DataAccess {
    final private HashMap<String, UserData> userDataHasMap = new HashMap<>();
    final private HashMap<String, AuthData> authDataHashMap = new HashMap<>();
    final private ArrayList<GameData> games = new ArrayList<GameData>();
    final private ArrayList<PublicGame> returnable = new ArrayList<PublicGame>();


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
    public void createPublic(PublicGame pub) {
        returnable.add(pub);
    }


    @Override
    public int gameID() {
        return games.size();
    }

    @Override
    public ArrayList<PublicGame> gameReturn() {
        return returnable;
    }

    @Override
    public GameData returnSingleGame(int gameID) {
        if(gameID <= games.size()-1) {
            return games.get(gameID);
        }
        else{
            return null;
        }
    }

    @Override
    public PublicGame editPublic(int gameID) {
        if(gameID <= returnable.size()) {
            return returnable.get(gameID);
        }
        else{
            return null;
        }
    }

    @Override
    public void updateGames(GameData game, PublicGame pub, int GameID) {
        games.set(GameID, game);
        returnable.set(GameID, pub);
    }

    @Override
    public void clearAuth() {
        authDataHashMap.clear();
        System.out.println("Hello there!");
    }

    @Override
    public void clearUsers() {
        userDataHasMap.clear();
        System.out.println("Hello there!");
    }

    @Override
    public void clearGames() {
        games.clear();
        returnable.clear();
    }

}
