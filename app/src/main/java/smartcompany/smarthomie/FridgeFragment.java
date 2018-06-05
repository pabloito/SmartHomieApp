package smartcompany.smarthomie;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment {

    Fridge fridge;

    public FridgeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fridge = (Fridge) ((MainActivity)getActivity()).getCurrentDevice();

        final EditText fridgeTempButton = (EditText) view.findViewById(R.id.fridge_temp_button);
        final EditText refridgeratorTempButton = (EditText) view.findViewById(R.id.refridgerator_temp_button);
        final Spinner fridgeModeSelect = (Spinner) view.findViewById(R.id.fridge_mode_select);
        final Button removeButton = (Button) view.findViewById(R.id.fridge_remove_button);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.fridge_mode_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fridgeModeSelect.setAdapter(adapter);
        fridgeModeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                System.out.println(selectedItemView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
            }

        });
    }
}
