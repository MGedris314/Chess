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

    public RegisterResult LogUser(UserData user_info){
        UserData return_val = dataAccess.findUser((user_info.username()));
        if(return_val!= null){
            if(return_val.password().equals(user_info.password())){
                AuthData authorize = linkAuth(user_info);
                RegisterResult regResult = new RegisterResult(authorize.authToken(), authorize.userName());
                return regResult;
            }
        }
//      If the data doesn't return null we continue.  To do so we need to check the passwords to see if they match. Figure out how to check hash maps.
        return null;
    }

    public UserData createUser(UserData user_info){
//        create and call a function for adding User names to the database inside dataaccess
        dataAccess.addUser(user_info.username(), user_info);
        return null;
    }

    public boolean authenticate(String authData){
        AuthData allowed = dataAccess.findAuth(authData);
        if(allowed != null) {
            return true;
        }
        else {return false;}
    }

    public String logOut(String authData){
        boolean is_valid = authenticate(authData);
        if (is_valid){
            dataAccess.removeAuth(authData);
        }
        if(!is_valid){
            System.out.println("Something went wrong");
            return null;
        }
        System.out.println("We're in.");
        String return_message = "";
        return return_message;
    }

    public static String AuthGeneration(){
        return UUID.randomUUID().toString();
    }

    public AuthData linkAuth(UserData user_info){
        String authToken = AuthGeneration();
        AuthData authorized = new AuthData(authToken, user_info.username());
        dataAccess.addAuthToken(authorized);
        return authorized;
    }

}
