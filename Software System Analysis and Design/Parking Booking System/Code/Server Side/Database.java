public class Database {
    private String connection;

    public Database(String con) {
        this.connection = con;
    }

    public void create(Object new);
    public void read(Object toRead);
    public void update(Object data);
    public void delete(Object toDelete);
}