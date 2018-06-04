package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoorFragment extends Fragment {


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

        final Button doorButton = (Button) view.findViewById(R.id.door_button);
        final TextView doorText= (TextView) view.findViewById(R.id.door_text);
        final Button lockButton = (Button) view.findViewById(R.id.lock_button);
        final TextView lockText= (TextView) view.findViewById(R.id.lock_text);
        final Button removeButton = (Button) view.findViewById(R.id.door_remove_button);

        doorButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String str = getResources().getString(R.string.door_button_off);

                if(doorButton.getText().equals(str)){ //TURINING ON CASE
                    doorButton.setText(R.string.door_button_on);
                    doorText.setText(R.string.door_text_on);
                }
                else{ //TURNING OFF CASE
                    doorButton.setText(R.string.door_button_off);
                    doorText.setText(R.string.door_text_off);
                }
            }
        });
        lockButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String str = getResources().getString(R.string.lock_button_off);

                if(lockButton.getText().equals(str)){ //TURINING ON CASE
                    lockButton.setText(R.string.lock_button_on);
                    lockText.setText(R.string.lock_text_on);
                }
                else{ //TURNING OFF CASE
                    lockButton.setText(R.string.lock_button_off);
                    lockText.setText(R.string.lock_text_off);
                }
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