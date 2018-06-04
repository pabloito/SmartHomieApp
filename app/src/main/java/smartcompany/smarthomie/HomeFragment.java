package smartcompany.smarthomie;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton newDevButton = (ImageButton) view.findViewById(R.id.new_device_button_home);
        final ImageButton newRoutineButton= (ImageButton) view.findViewById(R.id.new_routine_button_home);

        newDevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).externalSetFragment("newDeviceFragment");
            }
        });

        newRoutineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).externalSetFragment("newRoutineFragment");
            }
        });

        ((MainActivity)getActivity()).updateRoutines();
        ((MainActivity)getActivity()).updateDevices();

        drawDevices(view);
        drawRoutines(view);
    }

    public void addDevice(View view, Device device){
        LinearLayout devices = view.findViewById(R.id.home_devices);
        View deviceView = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);

        TextView t = deviceView.findViewById(R.id.item_name);
        t.append(device.name);

        devices.addView(deviceView);


        View imageView = deviceView.findViewById(R.id.image_view);

        switch(device.type){
            case "Curtain":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MainActivity ma = ((MainActivity)getActivity());
                        View parent = (View)v.getParent();
                        String name = ((TextView)parent.findViewById(R.id.item_name)).getText().toString();
                        Device d = ma.getDevicesMap().get(name);
                        ma.setCurrentDevice(d);
                        ma.externalSetFragment("curtainFragment");
                    }
                });
                break;
            case "Fridge":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MainActivity ma = ((MainActivity)getActivity());
                        View parent = (View)v.getParent();
                        String name = ((TextView)parent.findViewById(R.id.item_name)).getText().toString();
                        Device d = ma.getDevicesMap().get(name);
                        ma.setCurrentDevice(d);
                        ma.externalSetFragment("fridgeFragment");
                    }
                });
                break;
            case "Door":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MainActivity ma = ((MainActivity)getActivity());
                        View parent = (View)v.getParent();
                        String name = ((TextView)parent.findViewById(R.id.item_name)).getText().toString();
                        Device d = ma.getDevicesMap().get(name);
                        ma.setCurrentDevice(d);
                        ma.externalSetFragment("doorFragment");
                    }
                });
                break;
            case "Light":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MainActivity ma = ((MainActivity)getActivity());
                        View parent = (View)v.getParent();
                        String name = ((TextView)parent.findViewById(R.id.item_name)).getText().toString();
                        Device d = ma.getDevicesMap().get(name);
                        ma.setCurrentDevice(d);
                        ma.externalSetFragment("lightFragment");
                    }
                });
                break;
            case "Oven":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MainActivity ma = ((MainActivity)getActivity());
                        View parent = (View)v.getParent();
                        String name = ((TextView)parent.findViewById(R.id.item_name)).getText().toString();
                        Device d = ma.getDevicesMap().get(name);
                        ma.setCurrentDevice(d);
                        ma.externalSetFragment("ovenFragment");
                    }
                });
                break;
        }
    }


    public void addRoutine(View view, Routine routine){
        LinearLayout routines = view.findViewById(R.id.home_routines);
        View routineView = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);

        TextView t = routineView.findViewById(R.id.item_name);
        t.append(routine.name);

        routines.addView(routineView);

        View imageView = routineView.findViewById(R.id.image_view);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "HANDLER WORKS",
                        Toast.LENGTH_LONG).show();;
            }
        });
    }

    public void drawDevices(View view){
        MainActivity m = (MainActivity) getActivity();
        m.updateDevices();

        HashMap<String, Device> dmap = m.getDevicesMap();

        for(String key : dmap.keySet()){
            addDevice(view, dmap.get(key));
        }
    }

    public void drawRoutines(View view){
        MainActivity m = (MainActivity) getActivity();
        m.updateDevices();

        HashMap<String, Routine> rmap = m.getRoutinesMap();

        for(String key : rmap.keySet()){
            addRoutine(view, rmap.get(key));
        }
    }
}