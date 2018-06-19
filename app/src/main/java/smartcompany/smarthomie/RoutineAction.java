package smartcompany.smarthomie;

import java.util.List;

public class RoutineAction {
    private String deviceId;
    private String actionName;
    private List<Object> parameters;

    public RoutineAction(String deviceId, String actionName, List<Object> parameters) {
        this.actionName = actionName;
        this.deviceId = deviceId;
        this.parameters = parameters;
    }

    public void performAction() {
        Device actionDevice = API.GetDeviceById(deviceId);
        if(actionDevice != null && parameters == null) API.SendEvent(actionDevice,actionName);
        else if(actionDevice != null)   API.SendEventWithParameters(actionDevice,actionName,parameters);
    }
}
