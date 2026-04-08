package dataaccess;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryDataAccess implements DataAccess {
    final private HashMap<String, UserData> userDataHasMap = new HashMap<>();
    final private HashMap<String, AuthData> authDataHashMap = new HashMap<>();
    final private HashMap<Integer, PublicGame> publicGameHashMap = new HashMap<>();
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
        publicGameHashMap.put(pub.gameID(), pub);
    }


    @Override
    public int gameID() {
        return games.size() + 1;
    }

    @Override
    public GameRetrun gameReturn() {
        return new GameRetrun(returnable);
    }

    @Override
    public GameData returnSingleGame(int gameID) {
        if(gameID <= games.size() && gameID > 0) {
            return games.get(gameID-1);
        }
        else{
            return null;
        }
    }

    @Override
    public PublicGame editPublic(int gameID) {
        if(gameID <= returnable.size()) {
            return returnable.get(gameID-1);
        }
        else{
            return null;
        }
    }

    @Override
    public void Hello_there(GameData game, int gameID) throws DataAccessException {
        System.out.println("General Kenobi!");
    }


    @Override
    public void updateGames(GameData game, PublicGame pub, int gameid) {
        games.set(gameid-1, game);
        returnable.set(gameid-1, pub);
    }

    @Override
    public void clearAuth() {
        authDataHashMap.clear();
    }

    @Override
    public void clearUsers() {
        userDataHasMap.clear();
    }

    @Override
    public void clearGames() {
        games.clear();
        returnable.clear();
    }

}
