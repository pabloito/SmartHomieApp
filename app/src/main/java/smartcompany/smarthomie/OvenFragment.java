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
public class OvenFragment extends Fragment {

    Oven oven;
    Routine routine;

    public OvenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_oven, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        oven = (Oven) ((MainActivity)getActivity()).getCurrentDevice();

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


        final SeekBar ovenTemperatureSlider = (SeekBar) view.findViewById(R.id.oven_temperature_slider);
        final Spinner ovenConvectionSelect = (Spinner) view.findViewById(R.id.oven_convection_select);
        final Spinner ovenGrillSelect = (Spinner) view.findViewById(R.id.oven_grill_select);
        final Spinner ovenHeatSelect = (Spinner) view.findViewById(R.id.oven_heat_select);
        final Spinner ovenStateSelect = (Spinner) view.findViewById(R.id.oven_state_select);
        final Button removeButton = (Button) view.findViewById(R.id.oven_remove_button);

        ovenTemperatureSlider.setProgress(oven.getTemperature()-90);
        ovenConvectionSelect.setSelection(oven.getConvectionIndex());
        ovenGrillSelect.setSelection(oven.getGrillIndex());
        ovenHeatSelect.setSelection(oven.getHeatIndex());
        ovenStateSelect.setSelection(oven.getStateIndex());

        ovenTemperatureSlider.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int min=90;
                    int max=230;
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
                        oven.setTemperature(curr);
                    }
                });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.oven_state_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ovenStateSelect.setAdapter(adapter);
        ovenStateSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.oven_state_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);
                oven.setState(array[index]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
                oven.setState(null);
            }

        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.oven_convection_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ovenConvectionSelect.setAdapter(adapter2);
        ovenConvectionSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.oven_convection_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);
                oven.setConvection(array[index]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
                oven.setConvection(null);
            }

        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getContext(),R.array.oven_grill_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ovenGrillSelect.setAdapter(adapter3);
        ovenGrillSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.oven_grill_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);
                oven.setGrill(array[index]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
                oven.setGrill(null);
            }

        });

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getContext(),R.array.oven_heat_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ovenHeatSelect.setAdapter(adapter4);
        ovenHeatSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.oven_heat_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);
                oven.setHeat(array[index]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
                oven.setHeat(null);
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
