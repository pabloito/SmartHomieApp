package smartcompany.smarthomie;

import java.util.List;

public class RoutineAction {
    private String deviceId;
    private String actionName;
    private List<Object> params;

    public RoutineAction(String deviceId, String actionName, List<Object> params) {
        this.actionName = actionName;
        this.deviceId = deviceId;
        this.params = params;
    }

    public void performAction() {
        Device actionDevice = API.GetDeviceById(deviceId);
        if(actionDevice != null && params == null) API.SendEvent(actionDevice,actionName);
        else if(actionDevice != null)   API.SendEventWithParameters(actionDevice,actionName, params);
    }

    public String getDeviceId() {
        return deviceId;
    }
}
