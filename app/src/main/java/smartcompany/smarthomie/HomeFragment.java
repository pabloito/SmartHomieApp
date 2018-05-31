package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


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

        addDevice(view);
        addDevice(view);
        addDevice(view);
        addDevice(view);
        addDevice(view);
        addDevice(view);
        addDevice(view);
        addRoutine(view);
        addRoutine(view);
        addRoutine(view);
        addRoutine(view);
        addRoutine(view);
        addRoutine(view);
        addRoutine(view);
    }

    public void addDevice(View view){
        LinearLayout devices = view.findViewById(R.id.home_devices);
        View device = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);
        devices.addView(device);
    }

    public void addRoutine(View view){
        LinearLayout routines = view.findViewById(R.id.home_routines);
        View routine = getLayoutInflater().inflate(R.layout.layout_listitem,((ViewGroup)getView().getParent()),false);
        routines.addView(routine);
    }
}