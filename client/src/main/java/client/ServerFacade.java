package client;

import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import model.*;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;
    public ServerFacade(String url) {
        serverUrl = url;
    }

    public AuthData addUser(UserData ctx){
        var request = buildRequest("POST", "/user", ctx, null);
        var result =sendRequest(request);
        return handleResponse(result, AuthData.class);
//        returns auth data
    }

    public AuthData logI(UserData ctx){
        var request = buildRequest("POST", "/session", ctx, null);
        var result = sendRequest(request);
        return handleResponse(result, AuthData.class);
        //        returns auth data
    }

    public String logO(String token){
        var request = buildRequest("DELETE", "/session", null, token);
        var result = sendRequest(request);
        return handleResponse(result, null);
    }

    public GameRetrun listGame(String token){
        var request = buildRequest("GET", "/game", null, token);
        var result = sendRequest(request);
        return handleResponse(result, GameRetrun.class);
    }

    public GameData createGame(GameName ctx, String token){
        var request = buildRequest("POST", "/game", ctx, token);
        var result = sendRequest(request);
        return handleResponse(result, null);
    }

    public String joinGame(Context ctx, String token){
        var request = buildRequest("PUT", "/game", ctx, token);
        var result = sendRequest(request);
        return handleResponse(result, String.class);
    }

    private HttpRequest buildRequest(String method, String path, Object body, String header) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        if (header != null){
            request.setHeader("Authorization", header);
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
