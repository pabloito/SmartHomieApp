package smartcompany.smarthomie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoutinesFragment extends Fragment {


    public RoutinesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routines, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button newDevButton = (Button) view.findViewById(R.id.new_routine_button_routines);

        newDevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).externalSetFragment("newRoutineFragment");
            }
        });

        addRoutine(view, new Routine("a dormir lok"));
        addRoutine(view, new Routine("a dormir lok"));
        addRoutine(view, new Routine("a dormir lok"));
        addRoutine(view, new Routine("a dormir lok"));
        addRoutine(view, new Routine("a dormir lok"));
        addRoutine(view, new Routine("a dormir lok"));
        addRoutine(view, new Routine("a dormir lok"));
        addRoutine(view, new Routine("a dormir lok"));
        addRoutine(view, new Routine("a dormir lok"));
    }

    public void addRoutine(View view, Routine routine){
        TableLayout devices = view.findViewById(R.id.routines_scroll);
        View routineView = getLayoutInflater().inflate(R.layout.layout_routines_item,((ViewGroup)getView().getParent()),false);

        devices.addView(routineView);

        TextView t = routineView.findViewById(R.id.item_name);
        t.append(routine.name);

        ImageButton delete = routineView.findViewById(R.id.routine_delete);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "DELETE HANDLER WORKS",
                        Toast.LENGTH_LONG).show();;
            }
        });

        ImageButton execute = routineView.findViewById(R.id.routine_execute);
        execute.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "EXECUTE HANDLER WORKS",
                        Toast.LENGTH_LONG).show();;
            }
        });

        View imageView = routineView.findViewById(R.id.image_view);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "HANDLER WORKS",
                        Toast.LENGTH_LONG).show();;
            }
        });
    }
}
