package smartcompany.smarthomie;

public class Curtain extends Device {
    private boolean raised;
    public Curtain(String name) {
        super(name);
    }

    public Curtain(String name, String type) {
        super(name, type);
        raised=true;
    }

    public void raiseCurtain(){
        raised=true;

    }
    public void lowerCurtain(){
        raised=false;
    }
    public boolean isRaised() {
        return raised;
    }
}
