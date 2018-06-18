package smartcompany.smarthomie;

import android.content.Context;

public class Curtain extends Device {
    private boolean raised;

    public Curtain(String id, String name, String meta) {
        super(id, name, DevicesTypes.BLIND.TypeId(), meta);
        raised=true;
    }

    public Curtain(String id, String name,Context context) {
        super(id, name, DevicesTypes.BLIND.TypeId(), null);
        raised=true;
    }

    public void raiseCurtain(){ raised=true; }

    public void lowerCurtain(){
        raised=false;
    }

    public boolean isRaised() {
        return raised;
    }
}
