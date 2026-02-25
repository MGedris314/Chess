package dataaccess;

import model.UserData;
import model.AuthData;

import java.util.HashMap;

public class MemoryDataAccess implements DataAccess {
    final private HashMap<String, UserData> userDataHasMap = new HashMap<>();
    final private HashMap<String, AuthData> authDataHashMap = new HashMap<>();


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
        authDataHashMap.put(authToken.authToken(), authToken);
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

}
