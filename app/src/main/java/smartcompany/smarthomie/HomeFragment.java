package smartcompany.smarthomie;


import android.os.Bundle;
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

        addDevice(view,new Device("La hermna d fer"));
        addDevice(view,new Device("La hermna d fer"));
        addDevice(view,new Device("La hermna d fer"));
        addDevice(view,new Device("La hermna d fer"));
        addDevice(view,new Device("La hermna d fer"));
        addDevice(view,new Device("La hermna d fer"));
        addDevice(view,new Device("La hermna d fer"));
        addDevice(view,new Device("La hermna d fer"));
        addRoutine(view,new Routine("Me voy"));
        addRoutine(view,new Routine("Me voy"));
        addRoutine(view,new Routine("Me voy"));
        addRoutine(view,new Routine("Me voy"));
        addRoutine(view,new Routine("Me voy"));
        addRoutine(view,new Routine("Me voy"));
        addRoutine(view,new Routine("Me voy"));
        addRoutine(view,new Routine("Me voy"));
    }

    public void addDevice(View view, Device device){
        LinearLayout devices = view.findViewById(R.id.home_devices);
        View deviceView = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);

        TextView t = deviceView.findViewById(R.id.item_name);
        t.append(device.name);

        devices.addView(deviceView);
    }

    public void addRoutine(View view, Routine routine){
        LinearLayout routines = view.findViewById(R.id.home_routines);
        View routineView = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);

        TextView t = routineView.findViewById(R.id.item_name);
        t.append(routine.name);

        routines.addView(routineView);
    }
}