package smartcompany.smarthomie;

public enum DevicesTypes {
    BLIND("eu0v2xgprrhhg41g","curtain"),
    OVEN("im77xxyulpegfmv8","oven"),
    REFRIGERATOR("rnizejqr2di0okho","fridge"),
    DOOR("lsf78ly0eqrjbz91","door"),
    LAMP("go46xmbqeomjrsjr","light");

    private String typeId;
    private String typeName;

    DevicesTypes(String typeId,String typeName){
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public String TypeId() {
        return this.typeId;
    }

    public String TypeName() {
        return this.typeName;
    }

    public static String TypeName(String typeId) {
        switch (typeId) {
            case "eu0v2xgprrhhg41g":    return BLIND.TypeName();
            case "im77xxyulpegfmv8":    return OVEN.TypeName();
            case "rnizejqr2di0okho":    return REFRIGERATOR.TypeName();
            case "lsf78ly0eqrjbz91":    return DOOR.TypeName();
            case "go46xmbqeomjrsjr":    return LAMP.TypeName();
            default:    return null;
        }
    }

    public static String TypeId(String typeName) {
        String aux = typeName.toLowerCase();
        switch (aux){
            case "curtain":   return  BLIND.TypeId();
            case "oven": return OVEN.TypeId();
            case "light": return LAMP.TypeId();
            case "door": return DOOR.TypeId();
            case "fridge": return REFRIGERATOR.TypeId();
            default: return null;
        }
    }
}
