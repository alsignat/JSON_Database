package server;

public class Database {

    private String[] database;

    Database(int capacity) {
        this.database = new String[capacity];
    }

    public String set(int index, String value) {
        String answer = "ERROR";
        if (index < database.length) {
            this.database[index] = value;
            answer = "OK";
        }
        return answer;
    }

    public String get(int index) {
        String answer = "ERROR";
        if (index < database.length) {
            answer = database[index];
        }
        return answer;
    }

    public String delete(int index) {
        String answer = "ERROR";
        if (index < database.length) {
            database[index] = null;
            answer = "OK";
        }
        return answer;
    }

}
