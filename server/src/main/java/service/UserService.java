package service;

import dataaccess.DataAccess;
import model.RegisterResult;
import model.UserData;

public class UserService {

    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public RegisterResult registration(UserData user_info){
        dataAccess.findUser(user_info.username());
        return null;
    }


}
