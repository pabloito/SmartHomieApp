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

    /*Convert a generic device to an specific device like blind, lamp, etc*/

    public static Device DeviceFactory(Device d){
        if(d.getTypeId().equals(DevicesTypes.BLIND.TypeId())) {
            return  new Curtain(d.getId(),d.getName(),d.getMeta());
        }else if(d.getTypeId().equals(DevicesTypes.LAMP.TypeId())) {
            return  new Light(d.getId(),d.getName(),d.getMeta());
        }else if(d.getTypeId().equals(DevicesTypes.REFRIGERATOR.TypeId())) {
            return  new Fridge(d.getId(),d.getName(),d.getMeta());
        }else if(d.getTypeId().equals(DevicesTypes.OVEN.TypeId())) {
            return  new Oven(d.getId(),d.getName(),d.getMeta());
        }else if(d.getTypeId().equals(DevicesTypes.DOOR.TypeId())){
            return  new Door(d.getId(),d.getName(),d.getMeta());
        }else {
            return null;
        }
    }
}
