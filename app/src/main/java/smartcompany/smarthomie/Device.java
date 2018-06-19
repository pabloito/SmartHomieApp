package smartcompany.smarthomie;

public class Device {
     String name;
     String typeId;
     String id;
     String meta;

    public Device(String id, String name, String typeId, String meta) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.meta = meta;
    }

    public String getName(){
        return name;
    }

    public String getId() {
        return id;
    }

    public String getMeta() {
        return meta;
    }

    public String getTypeId() {
        return typeId;
    }

    public void removeDevice(){
        API.RemoveDevice(this);
    }

    /*Convert a generic device to an specific device like blind, lamp, etc*/

    public static Device DeviceFactory(Device d){
        if(d.getTypeId().equals(DevicesTypes.BLIND.TypeId())) {
            Curtain curtain = new Curtain(d.getId(),d.getName(),d.getMeta());
            curtain.updateStatus();
            return  curtain;
        }else if(d.getTypeId().equals(DevicesTypes.LAMP.TypeId())) {
            Light l = new Light(d.getId(),d.getName(),d.getMeta());
            l.updateStatus();
            return  l;
        }else if(d.getTypeId().equals(DevicesTypes.REFRIGERATOR.TypeId())) {
            Fridge f = new Fridge(d.getId(),d.getName(),d.getMeta());
            f.updateStatus();
            return  f;
        }else if(d.getTypeId().equals(DevicesTypes.OVEN.TypeId())) {
            Oven oven = new Oven(d.getId(),d.getName(),d.getMeta());
            oven.updateStatus();
            return  oven;
        }else if(d.getTypeId().equals(DevicesTypes.DOOR.TypeId())){
            Door door = new Door(d.getId(),d.getName(),d.getMeta());
            door.updateStatus();
            return  door;
        }else {
            return null;
        }
    }
}
