package smartcompany.smarthomie;

import android.content.Context;

public class Door extends Device {
    private boolean locked;
    private boolean closed;

    public Door(String id,String name, String meta) {
        super(id ,name, DevicesTypes.DOOR.TypeId(), meta);
        locked=false;
        closed=false;
    }

    public Door(String id, String name) {
        super(id, name, DevicesTypes.DOOR.TypeId(),null);
        locked=false;
        closed=false;
    }

    public void lock() {
        locked=true;
        API.SendEvent(this,"lock");
    }
    public void unlock() {
        locked =false;
        API.SendEvent(this,"unlock");
    }
    public void open() {
        closed=false;
        API.SendEvent(this,"open");
    }
    public void close() {
        closed=true;
        API.SendEvent(this,"close");
    }
    public boolean isLocked(){
        return locked;
    }
    public boolean isClosed(){
        return closed;
    }
}
