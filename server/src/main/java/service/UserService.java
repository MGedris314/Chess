package service;

import dataaccess.DataAccess;
import model.AuthData;
import model.RegisterResult;
import model.UserData;
import exception.*;
import org.eclipse.jetty.server.Authentication;

import java.util.UUID;


public class UserService {

    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public RegisterResult GetUser(UserData user_info) throws UserException403{
        UserData return_val = dataAccess.findUser(user_info.username());
        if(return_val == null) {
            dataAccess.addUser(user_info.username(), user_info);
            AuthData authorize = linkAuth(user_info);
            RegisterResult regResult = new RegisterResult(authorize.authToken(), authorize.userName());
            return regResult;
        }
        else{
            throw new UserException403("403: Error: bad request");
        }
    }

    public RegisterResult LogUser(UserData user_info) throws UserException401 {
        UserData return_val = dataAccess.findUser((user_info.username()));
        if(return_val!= null){
            if(return_val.password().equals(user_info.password())){
                AuthData authorize = linkAuth(user_info);
                RegisterResult regResult = new RegisterResult(authorize.authToken(), authorize.userName());
                return regResult;
            }
            else{
                throw new UserException401("401: Error: unauthorized");
            }
        }
        throw new UserException401("401: Error: unauthorized");
    }

    public boolean authenticate(String authData){
        AuthData allowed = dataAccess.findAuth(authData);
        if(allowed != null) {
            return true;
        }
        else {return false;}
    }

    public String logOut(String authData) throws UserException401{
        boolean is_valid = authenticate(authData);
        if (is_valid){
            dataAccess.removeAuth(authData);
        }
        if(!is_valid){
            throw new UserException401("401: Error: unauthorized");
        }
        System.out.println("We're in.");
        String return_message = "{}";
        return return_message;
    }

    public AuthData linkAuth(UserData user_info){
        if(user_info.username().isEmpty()){
            return null;
        }
        String authToken = UUID.randomUUID().toString();
        AuthData authorized = new AuthData(authToken, user_info.username());
        dataAccess.addAuthToken(authorized);
        return authorized;
    }

    public void DBClear(){
        dataAccess.clearUsers();
        dataAccess.clearAuth();
        dataAccess.clearGames();
    }

}
