package server;

import dataaccess.DataAccessException;
import dataaccess.SQLDataAccess;
import exception.*;
import handler.UserHandler;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.Javalin;
import websocket.WebsocketHandler;

import java.util.Map;


public class Server {
    private final Javalin javalin;
    private final UserHandler handler;
    private final WebsocketHandler websock;

    public Server() {
        this(new UserHandler(new SQLDataAccess()), new WebsocketHandler());

        // Register your endpoints and exception handlers here.
    }
    public Server(UserHandler handler, WebsocketHandler websock){
        this.handler = handler;
        this.websock = websock;
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", this::addUser)
                .post("/session", this::logIn)
                .post("/game", this::createGame)
                .put("/game", this::joinGame)
                .get("game", this::listGames)
                .delete("/session", this::logOut)
                .delete("/db", this::fullClear)
                .ws("/ws", ws -> {
                    ws.onConnect(websock);
                    ws.onMessage(websock);
                });
    }

    private void addUser(Context ctx){
//        400 and 403
        try {
            String addin = handler.register(ctx.body());
            ctx.result(addin);
        }
        catch(UserExceptions e ){
            ctx.status(400);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 400)));
        }
        catch(UserException403 e ){
            ctx.status(403);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 403)));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 500)));
        }

    }

    private void logIn(Context ctx){
//        400 and 401
        try {
            String logged = handler.logIn(ctx.body());
            ctx.result(logged);
        }
        catch (UserExceptions e){
            ctx.status(400);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 400)));
        }
        catch (UserException401 e){
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 500)));
        }
    }

    private void createGame(Context ctx){
//        400 and 401
        try {
            boolean authentic = handler.authenticate(ctx.header("authorization"));
            if (authentic) {
                try {
                    String id = handler.addGame(ctx.body());
                    ctx.result(id);
                }
                catch (UserExceptions e){
                    ctx.status(400);
                    ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
                }
            }
        }
        catch (UserException401 e){
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 400)));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 400)));

        }

    }

    private void joinGame(Context ctx){
//        400, 401, and 403
        try{
        boolean authentic = handler.authenticate(ctx.header("authorization"));
            if (authentic) {
                try {
                    String token = ctx.header("authorization");
                    String joined = handler.joinGame(ctx.body(), token);
                    ctx.result(joined);
                }
                catch (UserExceptions e){
                    ctx.status(400);
                    ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 400)));
                }
                catch (UserException403 e){
                    ctx.status(403);
                    ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 403)));
                }
            }
        }
        catch (UserException401 e){
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        }
    }

    private void listGames(Context ctx){
        try {
            boolean authentic = handler.authenticate(ctx.header("authorization"));
            if (authentic) {
                String games = handler.getGames();
                ctx.result(games);
            }
        }
        catch (UserException401 e){
                ctx.status(401);
                ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        }
        catch (DataAccessException e){
        ctx.status(500);
        ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 500)));
        }
    }

    private void logOut(Context ctx){
        try {
            String logout = handler.logOut(ctx.header("authorization"));
            ctx.result(logout);
        }
        catch(UserException401 e){
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        }
    }

    private void fullClear(Context ctx) throws DataAccessException {
        try {
            String clearOut = handler.clearDB();
            ctx.result(clearOut);
        }
        catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 500)));
        }
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
