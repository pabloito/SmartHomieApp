package smartcompany.smarthomie;

import android.content.Context;

public class Door extends Device {
    private boolean locked;
    private boolean closed;

    public Door(String id,String name, String meta,Context context) {
        super(id ,name, DevicesTypes.DOOR.TypeId(), meta,context);
        locked=false;
        closed=false;
    }

    public Door(String id, String name,Context context) {
        super(id, name, DevicesTypes.DOOR.TypeId(),null,context);
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
