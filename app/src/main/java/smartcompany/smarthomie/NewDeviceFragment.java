package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewDeviceFragment extends Fragment {


    public NewDeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_device, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity ma = (MainActivity) getActivity();

        final EditText selectType = (EditText) view.findViewById(R.id.EntryName);
        final Button save = (Button) view.findViewById(R.id.buttonSave);
        final Button cancel = (Button) view.findViewById(R.id.buttonCancel);
        final TextView title = (TextView) view.findViewById(R.id.titleName);
        final Spinner dropdown = (Spinner) view.findViewById(R.id.dropdownTypes);

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectType.setText("");
                ((MainActivity)getActivity()).externalSetFragment("homeFragment");
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.devices,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = true;
                Pattern cmp = Pattern.compile("^[a-z|A-Z|0-9_]+( [a-z|A-Z|0-9_]+)*$");
                Editable str = selectType.getText();

                if(str.length() == 0){
                    Toast toast = Toast.makeText(getContext(), "El nombre debe tener al menos un caracter !",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    ok = false;
                }

                if(str.length() < 3){
                    Toast toast = Toast.makeText(getContext(), "El nombre debe tener al menos 3 caracteres !",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    ok = false;
                }

                Matcher real = cmp.matcher(str);

                if(!real.find()){
                    Toast toast = Toast.makeText(getContext(), "El nombre tiene caracteres inválidos !",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    ok = false;
                }
                if (API.getDevicesMap() != null){
                    if(API.getDevicesMap().containsKey(selectType.getText().toString())){
                      Toast toast = Toast.makeText(getContext(), "El nombre ya esta utilizado !",
                            Toast.LENGTH_SHORT);
                        toast.show();
                        ok = false;
                    }
                }

                if (ok) {
                    String type = dropdown.getSelectedItem().toString().toLowerCase();
                    type = DevicesTypes.TypeId(type);
                    API.AddNewDevice(new Device(null,selectType.getText().toString(),type,null));
                    ((MainActivity)getActivity()).externalSetFragment("homeFragment");
                }

            }
        });
    }
}
