package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import handler.UserHandler;
import io.javalin.*;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.Javalin;
import org.eclipse.jetty.server.Authentication;


public class Server {
    private final Javalin javalin;
    private final UserHandler handler;

    public Server() {
        this(new UserHandler(new MemoryDataAccess()));

        // Register your endpoints and exception handlers here.
    }
    public Server(UserHandler handler){
        this.handler = handler;
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", this::addUser)
                .post("/session", this::logIn)
                .post("/game", this::createGame)
                .put("/game", this::joinGame)
                .get("game", this::listGames)
                .delete("/session", this::logOut)
                .delete("/db", this::fullClear);
    }

    private void addUser(Context ctx){
        String addin = handler.register(ctx.body());
        ctx.result(addin);
    }

    private void logIn(Context ctx){

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
