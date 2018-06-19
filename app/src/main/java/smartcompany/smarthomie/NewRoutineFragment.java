package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewRoutineFragment extends Fragment {


    static ArrayList<Device> devices;

    @Override
    public void onStart() {
        super.onStart();
        devices = new ArrayList<>();
    }

    public NewRoutineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_routine, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).updateDevices();
        drawDevices(view);

        final Button save = (Button) view.findViewById(R.id.buttonSave2);
        final EditText name = (EditText) view.findViewById(R.id.EntryName2);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pattern cmp = Pattern.compile("^[a-z|A-Z|0-9_]+( [a-z|A-Z|0-9_]+)*$");
                Editable str = name.getText();
                boolean ok = true;

                if(str.length() == 0){
                    Toast toast = Toast.makeText(getContext(), getContext().getString(R.string.fail_4),
                            Toast.LENGTH_SHORT);
                    toast.show();
                    ok = false;
                    return;
                }

                if(str.length() < 3){
                    Toast toast = Toast.makeText(getContext(), getContext().getString(R.string.fail_3),
                            Toast.LENGTH_SHORT);
                    toast.show();
                    ok = false;
                    return;
                }

                Matcher real = cmp.matcher(str);

                if(!real.find()){
                    Toast toast = Toast.makeText(getContext(), getContext().getString(R.string.fail_2),
                            Toast.LENGTH_SHORT);
                    toast.show();
                    ok = false;
                    return;
                }

                if(API.getRoutineMap() != null){
                    if(API.getRoutineMap().containsKey(name.getText().toString())){
                        Toast toast = Toast.makeText(getContext(), getContext().getString(R.string.fail_1),
                                Toast.LENGTH_SHORT);
                        toast.show();
                        ok = false;
                        return;
                    }
                }

                ArrayList<RoutineAction> ra = new ArrayList<>();
                for(Device d : NewRoutineFragment.devices){
                    ra.add(new RoutineAction(d.id,"getState", new ArrayList<Object>()));
                }

                API.AddNewRoutine(new Routine(null,str.toString(),ra));
                ((MainActivity)getActivity()).externalSetFragment("homeFragment");
            }
        });
    }

    public void addDevices(View view, Device device){
        TableLayout devices = view.findViewById(R.id.new_routine_scroll);
        View newRoutineView = getLayoutInflater().inflate(R.layout.new_routine_listitem,((ViewGroup)getView().getParent()),false);

        devices.addView(newRoutineView);

        TextView t = newRoutineView.findViewById(R.id.item_name);
        t.append(device.name);

        CheckBox check = newRoutineView.findViewById(R.id.checkBox);
        check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                View p = (View)v.getParent().getParent().getParent();
                String name = ((TextView)p.findViewById(R.id.item_name)).getText().toString();
                if(((CheckBox)v).isChecked())
                    NewRoutineFragment.devices.add(((MainActivity)getActivity()).getDevicesMap().get(name));
                else
                    NewRoutineFragment.devices.remove(((MainActivity)getActivity()).getDevicesMap().get(name));
            }
        });

        ImageView im = newRoutineView.findViewById(R.id.image_view);
        Log.d("dev",device.getName());
        Log.d("dev",device.getTypeId());
        String deviceType = device.getTypeId();

        if(deviceType.equals(DevicesTypes.DOOR.TypeId())) {
            im.setImageResource(R.drawable.door);
        }else if(deviceType.equals(DevicesTypes.BLIND.TypeId())) {
            im.setImageResource(R.drawable.curtain);
        }else if(deviceType.equals(DevicesTypes.LAMP.TypeId())) {
            im.setImageResource(R.drawable.light);
        }else if(deviceType.equals(DevicesTypes.OVEN.TypeId())) {
            im.setImageResource(R.drawable.oven);
        }else if(deviceType.equals(DevicesTypes.REFRIGERATOR.TypeId())) {
            im.setImageResource(R.drawable.fridge);
        }
    }

    public void drawDevices(View view){
        MainActivity m = (MainActivity) getActivity();
        m.updateDevices();

        HashMap<String, Device> rmap = m.getDevicesMap();

        for(String key : rmap.keySet()){
            addDevices(view, rmap.get(key));
        }
    }

}
