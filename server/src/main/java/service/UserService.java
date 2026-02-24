package service;

import dataaccess.DataAccess;
import model.AuthData;
import model.RegisterResult;
import model.UserData;
import java.util.UUID;


public class UserService {

    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public RegisterResult GetUser(UserData user_info){
        UserData return_val = dataAccess.findUser(user_info.username());
        if(return_val == null) {
            createUser(user_info);
            AuthData authorize = linkAuth(user_info);
            RegisterResult regResult = new RegisterResult(authorize.authToken(), authorize.userName());
            return regResult;
        }
        return null;
    }

    public UserData createUser(UserData user_info){
//        create and call a function for adding User names to the database inside dataaccess
        dataAccess.addUser(user_info.username(), user_info);
        return null;
    }

    public static String AuthGeneration(){
        return UUID.randomUUID().toString();
    }

    public AuthData linkAuth(UserData user_info){
        String authToken = AuthGeneration();
        AuthData authorized = new AuthData(user_info.username(), authToken);
        dataAccess.addAuthToken(authorized);
        return authorized;
    }

}
