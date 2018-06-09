package smartcompany.smarthomie;

public class Door extends Device {
    private boolean locked;
    private boolean closed;
    public Door(String name) {
        super(name);
    }

    public Door(String name, String type) {
        super(name, type);
        locked=false;
        closed=false;
    }

    public void lock() {
        locked=true;

    }
    public void unlock() {
        locked =false;
    }
    public void open() {
        closed=false;
    }
    public void close() {
        closed=true;
    }
    public boolean isLocked(){
        return locked;
    }
    public boolean isClosed(){
        return closed;
    }
}
