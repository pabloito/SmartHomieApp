package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoutinesFragment extends Fragment {


    public RoutinesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routines, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button newDevButton = (Button) view.findViewById(R.id.new_routine_button_routines);

        newDevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).externalSetFragment("newRoutineFragment");
            }
        });

    }

    public void addRoutine(View view, Device device){/*
        LinearLayout devices = view.findViewById(R.id.devices_scroll);
        View deviceView = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);

        TextView t = deviceView.findViewById(R.id.item_name);
        t.append(device.name);

        devices.addView(deviceView);*/
    }
}
