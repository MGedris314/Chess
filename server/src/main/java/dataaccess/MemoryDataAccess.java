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
}
