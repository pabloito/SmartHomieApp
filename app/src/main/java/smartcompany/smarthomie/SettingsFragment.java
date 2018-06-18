package smartcompany.smarthomie;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.util.HashMap;

import static android.content.ContentValues.TAG;


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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();

        Button button = settingsView.findViewById(R.id.checkButton);
        int ret=prefs.getInt(device.getName(),3);


        Log.d("not",String.valueOf(ret));
        if(ret==1){
            Log.d("not","checking");
            button.setText(R.string.remove_notifications);
        }
        else {
            Log.d("not","unchecking");
            button.setText(R.string.add_notifications);
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity ma = ((MainActivity)getActivity());
                View parent = (View)v.getParent().getParent();
                String name = ((TextView)parent.findViewById(R.id.rel_layout).findViewById(R.id.item_name)).getText().toString();
                Device d = ma.getDevicesMap().get(name);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();
                Button button = v.findViewById(R.id.checkButton);

                int ret=prefs.getInt(d.name,3);

                if(ret!=1){
                    Log.d("NOTIF","put 1 in "+d.name);
                    editor.putInt(d.name,1);
                    button.setText(R.string.remove_notifications);
                }
                else{
                    Log.d("NOTIF","put 0 in "+d.name);
                    editor.putInt(d.name,0);
                    button.setText(R.string.add_notifications);
                }
                editor.commit();
            }
        });

        if(prefs.getInt(device.name,3)==3){
            editor.putInt(device.name, 0);
            editor.commit();
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
