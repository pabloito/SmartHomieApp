package smartcompany.smarthomie;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoutineFragment extends Fragment {

    Routine routine;

    public RoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAG",((MainActivity)getActivity()).getComesFromDeviceThatComesFromRoutine()?"true":"false");
        if(!((MainActivity)getActivity()).getComesFromDeviceThatComesFromRoutine())routine.actions=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routine, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        routine = (Routine) ((MainActivity)getActivity()).getCurrentRoutine();

        TextView name = (TextView) view.findViewById(R.id.routine_name);

        name.append(routine.name);

        ImageButton execute = (ImageButton) view.findViewById(R.id.routine_execute);
        ImageButton delete = (ImageButton) view.findViewById(R.id.routine_delete);
        android.support.v7.widget.AppCompatButton save = view.findViewById(R.id.save_routine_button);

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routine.execute();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routine.delete();
                ((MainActivity)getActivity()).externalSetFragment("homeFragment");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Routine n = new Routine(routine.id,routine.name,routine.actions);
                API.UpdateRoutine(n);
            }
        });

        drawDevices(view);
    }

    public void addDevices(View view, Device device){
        TableLayout devices = view.findViewById(R.id.routine_scroll);
        View routineView = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);

        devices.addView(routineView);

        TextView t = routineView.findViewById(R.id.item_name);
        t.append(device.getName());

        View imageView = routineView.findViewById(R.id.image_view);
        Log.d("a",device.getTypeId());

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
        //DEPRECATED: SE REPLAZO POR LO DE ARRIBA!
        /*
        switch(device.getTypeId()){
            case "Curtain":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MainActivity ma = ((MainActivity)getActivity());
                        View parent = (View)v.getParent();
                        String name = ((TextView)parent.findViewById(R.id.item_name)).getText().toString();
                        Device d = ma.getDevicesMap().get(name);
                        ma.setCurrentDevice(d);
                        ((MainActivity)getActivity()).externalSetFragment("curtainFragment");
                    }
                });
                break;
            case "Fridge":
                imageView.setImageResource(R.drawable.fridge);
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        MainActivity ma = ((MainActivity)getActivity());
                        View parent = (View)v.getParent();
                        String name = ((TextView)parent.findViewById(R.id.item_name)).getText().toString();
                        Device d = ma.getDevicesMap().get(name);
                        ma.setCurrentDevice(d);
                        ((MainActivity)getActivity()).externalSetFragment("fridgeFragment");
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
                        ((MainActivity)getActivity()).externalSetFragment("doorFragment");
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
                        ((MainActivity)getActivity()).externalSetFragment("lightFragment");
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
                        ((MainActivity)getActivity()).externalSetFragment("ovenFragment");
                    }
                });
                break;
            default:
                Log.d("a","default");
                break;
        }
        */
    }

    public void drawDevices(View view){
        HashMap<String, Device> rmap = routine.getRoutineDevices();

        for(String key : rmap.keySet()){
            addDevices(view, rmap.get(key));
        }
    }
}
