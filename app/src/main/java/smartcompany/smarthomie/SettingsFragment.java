package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).updateDevices();
        drawDevices(view);
    }

    public void addDevices(View view, Device device){
        TableLayout devices = view.findViewById(R.id.settings_scroll);
        View settingsView = getLayoutInflater().inflate(R.layout.layout_settings_listitem,((ViewGroup)getView().getParent()),false);

        devices.addView(settingsView);

        TextView t = settingsView.findViewById(R.id.item_name);
        t.append(device.name);

        ImageView imageView = settingsView.findViewById(R.id.image_view);

        switch(device.type){
            case "Curtain":
                imageView.setImageResource(R.drawable.curtain);
                break;
            case "Fridge":
                imageView.setImageResource(R.drawable.fridge);
                break;
            case "Door":
                imageView.setImageResource(R.drawable.door);
                break;
            case "Light":
                imageView.setImageResource(R.drawable.light);
                break;
            case "Oven":
                imageView.setImageResource(R.drawable.oven);
                break;

        }

        CheckBox check = settingsView.findViewById(R.id.checkBox);
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
