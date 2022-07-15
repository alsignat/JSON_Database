package server;

public class Database {

    private String[] database;

    Database(int capacity) {
        this.database = new String[100];
    }

    public void set(int index, String value) {
        this.database[index] = value;
        System.out.println("OK");
    }

    public void get(int index) {
        String value = database[index];
        if (value == null) throw new NullPointerException();
        System.out.println(value);
    }

    public void delete(int index) {
        database[index] = null;
        System.out.println("OK");
    }

}
