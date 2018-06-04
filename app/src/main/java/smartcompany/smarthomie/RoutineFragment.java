package smartcompany.smarthomie;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class RoutineFragment extends Fragment {

    Routine routine;

    public RoutineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routine, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        routine = (Routine) ((MainActivity)getActivity()).getCurrentRoutine();

        TextView name = (TextView) view.findViewById(R.id.routine_name);
        name.append(routine.name);

        ImageButton execute = (ImageButton) view.findViewById(R.id.routine_execute);
        ImageButton delete = (ImageButton) view.findViewById(R.id.routine_delete);

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "falta esta funcion porque no está la api",
                        Toast.LENGTH_LONG).show();
                routine.execute();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "falta esta funcion porque no está la api",
                        Toast.LENGTH_LONG).show();
                routine.delete();
            }
        });

    }
}
