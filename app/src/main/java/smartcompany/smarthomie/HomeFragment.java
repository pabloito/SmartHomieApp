package smartcompany.smarthomie;


import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    boolean first=true;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        Log.d("hola","onstart");
        super.onStart();
        drawDevicesRemote();
        drawRoutinesRemote();
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

        drawDevices(view);
        drawRoutines(view);

        if(first){
            (new Task()).execute(this);
            first = false;
        }
    }

    public void addDevice(View view, final Device device){
        LinearLayout devices = view.findViewById(R.id.home_devices);
        View deviceView = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);

        TextView t = deviceView.findViewById(R.id.item_name);
        t.append(device.getName());

        devices.addView(deviceView);


        ImageView imageView = (ImageView) deviceView.findViewById(R.id.image_view);

        imageView.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             MainActivity ma = ((MainActivity) getActivity());
                                             View parent = (View) v.getParent();
                                             String name = ((TextView) parent.findViewById(R.id.item_name)).getText().toString();
                                             Device d = ma.getDevicesMap().get(name);
                                             Toast.makeText(getContext(),DevicesTypes.TypeName(d.getTypeId()),Toast.LENGTH_LONG).show();
                                             ma.setCurrentDevice(d);
                                             ma.externalSetFragment(DevicesTypes.TypeName(d.getTypeId()) + "Fragment");
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
                MainActivity ma = ((MainActivity)getActivity());
                View parent = (View)v.getParent();
                String name = ((TextView)parent.findViewById(R.id.item_name)).getText().toString();
                Routine d = ma.getRoutinesMap().get(name);
                ma.setCurrentRoutine(d);
                ma.externalSetFragment("routineFragment");
            }
        });
    }

    public void drawDevices(View view){
        MainActivity m = (MainActivity) getActivity();
        m.updateDevices();

        LinearLayout devs = view.findViewById(R.id.home_devices);
        devs.removeAllViews();

        HashMap<String, Device> dmap = m.getDevicesMap();

        for(String key : dmap.keySet()){
            addDevice(view, dmap.get(key));
        }
    }

    public void drawRoutines(View view){
        MainActivity m = (MainActivity) getActivity();
        m.updateRoutines();

        LinearLayout routines = view.findViewById(R.id.home_routines);
        routines.removeAllViews();

        HashMap<String, Routine> rmap = m.getRoutinesMap();

        for(String key : rmap.keySet()){
            addRoutine(view, rmap.get(key));
        }
    }

    public void drawDevicesRemote(){
        drawDevices(getView());
    }

    public void drawRoutinesRemote(){
        drawRoutines(getView());
    }

}