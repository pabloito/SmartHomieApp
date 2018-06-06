package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LightFragment extends Fragment {

    Light light;
    Routine routine;

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

        MainActivity ma = (MainActivity)getActivity();

        if (ma.getComesFromRoutine()){
            routine = ma.getCurrentRoutine();
            LinearLayout routineLayout = view.findViewById(R.id.routine_lay);
            View routineView = getLayoutInflater().inflate(R.layout.routine_section_devices_layout, ((ViewGroup) getView().getParent()), false);
            routineLayout.addView(routineView);

            Button save = view.findViewById(R.id.backToRoutine);

            save.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ((MainActivity)getActivity()).externalSetFragment("routineFragment");
                }
            });
        }


        final SeekBar lampBrightnessSlider = (SeekBar) view.findViewById(R.id.lamp_brightness_slider);
        final Spinner lampColorSelect = (Spinner) view.findViewById(R.id.lamp_color_select);
        final Spinner lampStateSelect = (Spinner) view.findViewById(R.id.lamp_state_select);
        final Button removeButton = (Button) view.findViewById(R.id.lamp_remove_button);

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
                        light.setBrightness(curr);
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
                light.setState(array[index]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
                light.setState(null);
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
                light.setColor(array[index]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
                light.setColor(null);
            }

        });

        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast toast = Toast.makeText(getContext(), "Lo removiste!.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
