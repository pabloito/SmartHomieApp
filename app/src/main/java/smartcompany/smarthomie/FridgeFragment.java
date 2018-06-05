package smartcompany.smarthomie;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
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

        final SeekBar fridgeTempSlider = (SeekBar) view.findViewById(R.id.fridge_temp_slider);
        final SeekBar refridgeratorTempSlider = (SeekBar) view.findViewById(R.id.refridgerator_temp_slider);
        final Spinner fridgeModeSelect = (Spinner) view.findViewById(R.id.fridge_mode_select);
        final Button removeButton = (Button) view.findViewById(R.id.fridge_remove_button);

        fridgeTempSlider.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int min=-20;
                    int max=-8;
                    int curr;
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser)
                    {
                        curr=seekBar.getProgress()-(seekBar.getMax()-max);
                        System.out.println(curr);
                    }
                });
        refridgeratorTempSlider.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int min=2;
                    int max=8;
                    int curr;
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser)
                    {
                        curr=seekBar.getProgress()-(seekBar.getMax()-max);
                        System.out.println(curr);
                    }
                });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.fridge_mode_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fridgeModeSelect.setAdapter(adapter);
        fridgeModeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.fridge_mode_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
            }

        });
    }
}
