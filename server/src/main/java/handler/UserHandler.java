package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.UserData;
import service.UserService;

public class UserHandler {
    private DataAccess dataAccess;
    public UserHandler(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    UserService userService = new UserService(dataAccess);
    public String register(String data_asJSON) {
        UserData userdata = new Gson().fromJson(data_asJSON, UserData.class);
        userService.GetUser(userdata);
        return null;
    }

}
