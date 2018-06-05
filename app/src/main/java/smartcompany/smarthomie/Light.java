package smartcompany.smarthomie;

public class Light extends Device {

    private String state;
    private int brightness;
    private String color;

    public Light(String name) {
        super(name);
    }

    public Light(String name, String type) {
        super(name, type);
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
