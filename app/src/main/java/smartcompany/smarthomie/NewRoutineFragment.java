package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewRoutineFragment extends Fragment {


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
    }

    public void addDevices(View view, Device device){
        TableLayout devices = view.findViewById(R.id.new_routine_scroll);
        View newRoutineView = getLayoutInflater().inflate(R.layout.layout_settings_listitem,((ViewGroup)getView().getParent()),false);

        devices.addView(newRoutineView);

        TextView t = newRoutineView.findViewById(R.id.item_name);
        t.append(device.name);

        CheckBox check = newRoutineView.findViewById(R.id.checkBox);
        check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "check HANDLER WORKS",
                        Toast.LENGTH_LONG).show();;
            }
        });
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
