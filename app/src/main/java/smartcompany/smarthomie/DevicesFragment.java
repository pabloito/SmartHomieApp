package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


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


        ((MainActivity)getActivity()).updateDevices();
        drawDevices(view);
    }

    public void addDevice(View view, Device device){
        LinearLayout devices = view.findViewById(R.id.devices_scroll);
        View deviceView = getLayoutInflater().inflate(R.layout.layout_devices_item,((ViewGroup)getView().getParent()),false);

        TextView type = deviceView.findViewById(R.id.device_type);
        type.append(device.getTypeId());

        TextView t = deviceView.findViewById(R.id.item_name);
        t.append(device.getName());

        devices.addView(deviceView);

        ImageButton delete = deviceView.findViewById(R.id.device_delete);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "DELETE HANDLER WORKS",
                        Toast.LENGTH_LONG).show();;
            }
        });

        ImageView imageView = deviceView.findViewById(R.id.image_view);

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MainActivity ma = ((MainActivity)getActivity());
                View parent = (View)v.getParent();
                String name = ((TextView)parent.findViewById(R.id.item_name)).getText().toString();
                Device d = ma.getDevicesMap().get(name);
                ma.setCurrentDevice(d);
                ((MainActivity)getActivity()).externalSetFragment(DevicesTypes.TypeName(d.getTypeId()) + "Fragment");
            }
        });

        String deviceType = device.getTypeId();

        if(deviceType.equals(DevicesTypes.DOOR.TypeId())) {
            imageView.setImageResource(R.drawable.door);
        }else if(deviceType.equals(DevicesTypes.BLIND.TypeId())) {
            imageView.setImageResource(R.drawable.curtain);
        }else if(deviceType.equals(DevicesTypes.LAMP.TypeId())) {
            imageView.setImageResource(R.drawable.light);
        }else if(deviceType.equals(DevicesTypes.OVEN.TypeId())) {
            imageView.setImageResource(R.drawable.oven);
        }else if(deviceType.equals(DevicesTypes.REFRIGERATOR.TypeId())) {
            imageView.setImageResource(R.drawable.fridge);
        }
    }

    public void drawDevices(View view){
        MainActivity m = (MainActivity) getActivity();
        m.updateDevices();
        HashMap<String, Device> dmap = m.getDevicesMap();

        for(String key : dmap.keySet()){
            addDevice(view, dmap.get(key));
        }
    }
}
