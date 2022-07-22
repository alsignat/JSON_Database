package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class Database {

    private final HashMap<String, String> database;
    private final Gson gson;

    Database() {
        this.database = new HashMap<>();
        this.gson = new Gson();
    }

    public String set(String key, String value) {
        database.put(key, value);
        return getResponse(null);
    }

    public String get(String key) {
        String value = database.get(key);
        return (value == null ? noSuchElementResponse() : getResponse(value));
    }

    public String delete(String key) {
        String value = database.get(key);
        if (value != null) {
            database.remove(key);
            return getResponse(null);
        } return noSuchElementResponse();
    }

    private String noSuchElementResponse() {
        JsonObject answer = new JsonObject();
        answer.addProperty("response", "ERROR");
        answer.addProperty("reason", "No such key");
        return gson.toJson(answer);
    }

    private String getResponse(String value) {
        JsonObject answer = new JsonObject();
        answer.addProperty("response", "OK");
        if (value != null) {
            answer.addProperty("value", value);
        }
        return gson.toJson(answer);
    }

    public Gson getGson() {
        return this.gson;
    }

}
