package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.UserData;
import service.UserService;
import model.RegisterResult;

public class UserHandler {
    private DataAccess dataAccess;
    public UserHandler(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public String register(String data_asJSON) {
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(data_asJSON, UserData.class);
        RegisterResult regi = userService.GetUser(userdata);
        return new Gson().toJson(regi);
    }

}
