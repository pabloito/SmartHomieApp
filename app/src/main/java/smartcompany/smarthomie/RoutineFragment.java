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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "falta esta funcion porque no está la api",
                        Toast.LENGTH_LONG).show();
                routine.execute();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "falta esta funcion porque no está la api",
                        Toast.LENGTH_LONG).show();
                routine.delete();
            }
        });

        drawDevices(view);
    }

    public void addDevices(View view, Device device){
        TableLayout devices = view.findViewById(R.id.routine_scroll);
        View routineView = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);

        devices.addView(routineView);

        TextView t = routineView.findViewById(R.id.item_name);
        t.append(device.name);

        View imageView = view.findViewById(R.id.image_view);
        Log.d("a",imageView.toString());
        switch(device.type){
            case "Curtain":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).externalSetFragment("curtainFragment");
                    }
                });
                break;
            case "Fridge":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.d("a","CLICKO");
                        ((MainActivity)getActivity()).externalSetFragment("fridgeFragment");
                    }
                });
                break;
            case "Door":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).externalSetFragment("doorFragment");
                    }
                });
                break;
            case "Light":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).externalSetFragment("lightFragment");
                    }
                });
                break;
            case "Oven":
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).externalSetFragment("ovenFragment");
                    }
                });
                break;

        }
    }

    public void drawDevices(View view){
        HashMap<String, Device> rmap = routine.getRoutineDevices();

        for(String key : rmap.keySet()){
            addDevices(view, rmap.get(key));
        }
    }
}
