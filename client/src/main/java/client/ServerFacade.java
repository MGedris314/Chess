package client;

import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import model.AuthData;
import model.UserData;
import model.GameData;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;
    public ServerFacade(String url) {
        serverUrl = url;
    }

    public AuthData addUser(UserData ctx){
        var request = buildRequest("POST", "/user", ctx);
        var result =sendRequest(request);
        return handleResponse(result, AuthData.class);
//        returns auth data
    }

    public AuthData logI(UserData ctx){
        var request = buildRequest("POST", "/session", ctx);
        var result = sendRequest(request);
        return handleResponse(result, AuthData.class);
        //        returns auth data
    }

    public void logO(){
        var request = buildRequest("DELETE", "/session", null);
    }

    public void listGame(){
        var request = buildRequest("GET", "/game", null);
//        returns Public game
    }

    public void createGame(Context ctx){
        var request = buildRequest("POST", "/game", ctx);
    }

    public void joinGame(Context ctx){
        var request = buildRequest("PUT", "/game", ctx);
    }

    private HttpRequest buildRequest(String method, String path, Object body) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        return request.build();
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }



    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                var ctx = new Gson().fromJson(body, ErrorMessage.class);
                throw new ResponseException(ctx.message());
            }

            throw new ResponseException("Error had no message....");
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
