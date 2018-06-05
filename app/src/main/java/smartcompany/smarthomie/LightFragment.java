package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class LightFragment extends Fragment {

    Light light;

    public LightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_light, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        light = (Light) ((MainActivity)getActivity()).getCurrentDevice();

        final SeekBar lampBrightnessSlider = (SeekBar) view.findViewById(R.id.lamp_brightness_slider);
        final Spinner lampColorSelect = (Spinner) view.findViewById(R.id.lamp_color_select);
        final Spinner lampStateSelect = (Spinner) view.findViewById(R.id.lamp_state_select);
        final Button removeButton = (Button) view.findViewById(R.id.fridge_remove_button);

        lampBrightnessSlider.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int min=1;
                    int max=100;
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.lamp_state_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lampStateSelect.setAdapter(adapter);
        lampStateSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.lamp_state_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
            }

        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.lamp_color_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lampColorSelect.setAdapter(adapter2);
        lampColorSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.lamp_color_array);
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
