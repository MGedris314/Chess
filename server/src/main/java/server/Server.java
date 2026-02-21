package server;

import io.javalin.*;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.Javalin;


public class Server {

    private final Javalin javalin;

    public Server() {

        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        .post("/user", this::addUser);
        .post("/session", this::logIn);
        .post("/game", this::createGame);
        .put("/game", this::joinGame);
        .get("game", this::listGames);
        .delete("/session", this::logOut);
        .delete("/db", this::fullClear);
        // Register your endpoints and exception handlers here.


    }


    private void addUser(Context ctx){
        int x = 0;
    }
    private void logIn(Context ctx){
        int x = 0;
    }
    private void createGame(Context ctx){
        int x = 0;
    }
    private void joinGame(Context ctx){
        int x = 0;
    }
    private void listGames(Context ctx){
        int x = 0;
    }
    private void logOut(Context ctx){
        int x = 0;
    }
    private void fullClear(Context ctx){
        int x = 0;
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
