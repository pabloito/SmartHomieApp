package smartcompany.smarthomie;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoorFragment extends Fragment {

    Door door;
    Routine routine;

    public DoorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_door, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        door = (Door) ((MainActivity)getActivity()).getCurrentDevice();
        System.out.println(door);

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

        final TextView title = (TextView) view.findViewById(R.id.door_title);
        title.setText(door.getName());

        final Button doorButton = (Button) view.findViewById(R.id.door_button);
        final TextView doorText= (TextView) view.findViewById(R.id.door_text);
        final Button lockButton = (Button) view.findViewById(R.id.lock_button);
        final TextView lockText= (TextView) view.findViewById(R.id.lock_text);
        final Button removeButton = (Button) view.findViewById(R.id.door_remove_button);
        System.out.println("Is locked?: "+door.isLocked()+" isClosed?: "+door.isClosed());
        if(door.isClosed()){
            doorButton.setText(R.string.door_button_off);
            doorText.setText(R.string.door_text_off);
        }else{
            doorButton.setText(R.string.door_button_on);
            doorText.setText(R.string.door_text_on);
        }

        if(door.isLocked()){
            lockButton.setText(R.string.lock_button_off);
            lockText.setText(R.string.lock_text_off);
        }else{
            lockButton.setText(R.string.lock_button_on);
            lockText.setText(R.string.lock_text_on);
        }
        doorButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String str = getResources().getString(R.string.door_button_off);

                if(doorButton.getText().equals(str)){ //TURINING ON CASE
                    doorButton.setText(R.string.door_button_on);
                    doorText.setText(R.string.door_text_on);
                    door.open();
                }
                else{ //TURNING OFF CASE
                    doorButton.setText(R.string.door_button_off);
                    doorText.setText(R.string.door_text_off);
                    door.close();
                }
                System.out.println("Is locked?: "+door.isLocked()+" isClosed?: "+door.isClosed());
            }
        });
        lockButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String str = getResources().getString(R.string.lock_button_off);

                if(lockButton.getText().equals(str)){ //TURINING ON CASE
                    lockButton.setText(R.string.lock_button_on);
                    lockText.setText(R.string.lock_text_on);
                    door.unlock();
                }
                else{ //TURNING OFF CASE
                    lockButton.setText(R.string.lock_button_off);
                    lockText.setText(R.string.lock_text_off);
                    door.lock();
                }

                System.out.println("Is locked?: "+door.isLocked()+" isClosed?: "+door.isClosed());
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
