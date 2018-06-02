package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DevicesFragment extends Fragment {


    public DevicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_devices, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button newDevButton = (Button) view.findViewById(R.id.new_device_button_devices);

        newDevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).externalSetFragment("newDeviceFragment");
            }
        });

        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
        addDevice(view, new Device("freezer del quincho","Heladera"));
    }

    public void addDevice(View view, Device device){
        LinearLayout devices = view.findViewById(R.id.devices_scroll);
        View deviceView = getLayoutInflater().inflate(R.layout.layout_devices_item,((ViewGroup)getView().getParent()),false);

        TextView type = deviceView.findViewById(R.id.device_type);
        type.append(device.type);

        TextView t = deviceView.findViewById(R.id.item_name);
        t.append(device.name);

        devices.addView(deviceView);
    }
}
