package model;

import com.google.gson.*;

public record User(String name, String password, String authToken) {

    public User setUser(name){
        return new User(this.name, this.password, this.authToken)
    }
    public String toString(){
        return new Gson().toJason(this);
    }
}
