package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.RegisterResult;
import model.UserData;
import exception.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;


public class UserService {

    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public RegisterResult getUser(UserData userInfo) throws UserException403, DataAccessException {
        UserData returnVal = dataAccess.findUser(userInfo.username());
        if(returnVal == null) {
            dataAccess.addUser(userInfo.username(), userInfo);
            AuthData authorize = linkAuth(userInfo);
            RegisterResult regResult = new RegisterResult(authorize.authToken(), authorize.userName());
            return regResult;
        }
        else{
            throw new UserException403("403: Error: bad request");
        }
    }

    public RegisterResult logUser(UserData userInfo) throws UserException401, DataAccessException {
        UserData returnVal = dataAccess.findUser((userInfo.username()));
        if(returnVal!= null){
            if(BCrypt.checkpw(userInfo.password(), returnVal.password())){
                AuthData authorize = linkAuth(userInfo);
                RegisterResult regResult = new RegisterResult(authorize.authToken(), authorize.userName());
                return regResult;
            }
            else{
                throw new UserException401("401: Error: unauthorized");
            }
        }
        throw new UserException401("401: Error: unauthorized");
    }

    public boolean authenticate(String authData) throws DataAccessException {
        AuthData allowed = dataAccess.findAuth(authData);
        if(allowed != null) {
            return true;
        }
        else {return false;}
    }

    public String logOut(String authData) throws UserException401, DataAccessException {
        boolean isValid = authenticate(authData);
        if (isValid){
            dataAccess.removeAuth(authData);
        }
        if(!isValid){
            throw new UserException401("401: Error: unauthorized");
        }
        System.out.println("We're in.");
        String returnMessage = "{}";
        return returnMessage;
    }

    public AuthData linkAuth(UserData userInfo) throws DataAccessException {
        if(userInfo.username().isEmpty()){
            return null;
        }
        String authToken = UUID.randomUUID().toString();
        AuthData authorized = new AuthData(authToken, userInfo.username());
        dataAccess.addAuthToken(authorized);
        return authorized;
    }

    public void dbclear() throws DataAccessException {
        dataAccess.clearUsers();
        dataAccess.clearAuth();
        dataAccess.clearGames();
    }

}
