package smartcompany.smarthomie;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurtainFragment extends Fragment {

    public CurtainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curtain, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button curtainButton = (Button) view.findViewById(R.id.curtain_button);
        final TextView curtainText= (TextView) view.findViewById(R.id.curtain_text);
        final Button removeButton = (Button) view.findViewById(R.id.curtain_remove_button);

        curtainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String str = getResources().getString(R.string.curtain_button_off);

                if(curtainButton.getText().equals(str)){ //TURINING ON CASE
                    curtainButton.setText(R.string.curtain_button_on);
                    curtainText.setText(R.string.curtain_text_on);
                }
                else{ //TURNING OFF CASE
                    curtainButton.setText(R.string.curtain_button_off);
                    curtainText.setText(R.string.curtain_text_off);
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
