package smartcompany.smarthomie;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class OvenFragment extends Fragment {


    public boolean inhibit_spinner1=false;
    public boolean inhibit_spinner2=false;
    public boolean inhibit_spinner3=false;
    public boolean inhibit_spinner4=false;
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

        createNotificationChannel();

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

        final TextView title = (TextView) view.findViewById(R.id.oven_title);
        title.setText(oven.getName());
        final SeekBar ovenTemperatureSlider = (SeekBar) view.findViewById(R.id.oven_temperature_slider);
        final Spinner ovenConvectionSelect = (Spinner) view.findViewById(R.id.oven_convection_select);
        final Spinner ovenGrillSelect = (Spinner) view.findViewById(R.id.oven_grill_select);
        final Spinner ovenHeatSelect = (Spinner) view.findViewById(R.id.oven_heat_select);
        final Spinner ovenStateSelect = (Spinner) view.findViewById(R.id.oven_state_select);
        final Button removeButton = (Button) view.findViewById(R.id.oven_remove_button);

        ovenTemperatureSlider.setProgress(oven.getTemperature()-90);

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
                        MainActivity mainActivity = (MainActivity) getActivity();
                        if(mainActivity.getComesFromRoutine()){
                            List<Object> param = new LinkedList<>();
                            param.add(curr);
                            routine.actions.add(new RoutineAction(oven.id,"setTemperature",param));
                        }else{
                            oven.setTemperature(curr);
                            MainActivity m = (MainActivity) (getActivity());
                            if(m.allowsNotification(oven)) {
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                        .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                        .setContentTitle(getContext().getString(R.string.notification_title))
                                        .setContentText(getContext().getString(R.string.notification_text_before)+oven.name+getContext().getString(R.string.notification_text_after))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true);
                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                                notificationManager.notify(0, mBuilder.build());
                            }
                        }
                    }
                });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.oven_state_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ovenStateSelect.setAdapter(adapter);
        ovenStateSelect.setSelection(oven.getStateIndex(),false);
        ovenStateSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.oven_state_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);
                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity.getComesFromRoutine()){
                    String[] array2= API.getContext().getResources().getStringArray(R.array.oven_state_array_L);
                    if(array2[index].equals("on")){
                        routine.actions.add(new RoutineAction(oven.id,"turnOn",new ArrayList<Object>()));
                    }else{
                        routine.actions.add(new RoutineAction(oven.id,"turnOff",new ArrayList<Object>()));
                    }
                }else{
                    oven.setState(array[index]);
                    MainActivity m = (MainActivity) (getActivity());
                    if(m.allowsNotification(oven)) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                .setContentTitle(getContext().getString(R.string.notification_title))
                                .setContentText(getContext().getString(R.string.notification_text_before) + oven.name + getContext().getString(R.string.notification_text_after))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                        notificationManager.notify(0, mBuilder.build());
                    }
                }

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
        ovenConvectionSelect.setSelection(oven.getConvectionIndex(),false);
        ovenConvectionSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.oven_convection_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);
                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity.getComesFromRoutine()){

                    List<Object> param = new LinkedList<>();

                    switch(index){
                        case 0:
                            param.add("normal");
                            routine.actions.add(new RoutineAction(oven.id,"setConvection",param));
                            break;
                        case 1:
                            param.add("eco");
                            routine.actions.add(new RoutineAction(oven.id,"setConvection",param));
                            break;
                        case 2:
                            param.add("off");
                            routine.actions.add(new RoutineAction(oven.id,"setConvection",param));
                            break;
                    }
                }else {
                    oven.setConvection(array[index]);
                    MainActivity m = (MainActivity) (getActivity());
                    if (m.allowsNotification(oven)) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                .setContentTitle(getContext().getString(R.string.notification_title))
                                .setContentText(getContext().getString(R.string.notification_text_before) + oven.name + getContext().getString(R.string.notification_text_after))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                        notificationManager.notify(0, mBuilder.build());
                    }
                }
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
        ovenGrillSelect.setSelection(oven.getGrillIndex(),false);
        ovenGrillSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                MainActivity m = (MainActivity) (getActivity());
                String[] array = getResources().getStringArray(R.array.oven_grill_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);
                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity.getComesFromRoutine()){

                    List<Object> param = new LinkedList<>();

                    switch(index) {
                        case 0:
                            param.add("large");
                            routine.actions.add(new RoutineAction(oven.id, "setGrill", param));
                            break;
                        case 1:
                            param.add("eco");
                            routine.actions.add(new RoutineAction(oven.id,"setGrill", param));
                            break;
                        case 2:
                            param.add("off");
                            routine.actions.add(new RoutineAction(oven.id,"setGrill", param));
                            break;
                    }
                }else {

                    oven.setGrill(array[index]);

                    if (m.allowsNotification(oven)) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                .setContentTitle(getContext().getString(R.string.notification_title))
                                .setContentText(getContext().getString(R.string.notification_text_before) + oven.name + getContext().getString(R.string.notification_text_after))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                        notificationManager.notify(0, mBuilder.build());
                    }
                }
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
        ovenHeatSelect.setSelection(oven.getHeatIndex(),false);
        ovenHeatSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String[] array = getResources().getStringArray(R.array.oven_heat_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: " + array[index]);

                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity.getComesFromRoutine()){

                    List<Object> param = new LinkedList<>();

                    switch(index) {
                        case 0:
                            param.add("conventional");
                            routine.actions.add(new RoutineAction(oven.id,"setHeat", param));
                            break;
                        case 1:
                            param.add("superior");
                            routine.actions.add(new RoutineAction(oven.id, "setHeat", param));
                            break;
                        case 2:
                            param.add("inferior");
                            routine.actions.add(new RoutineAction(oven.id, "setHeat", param));
                            break;
                    }
                }else {
                    oven.setHeat(array[index]);
                    MainActivity m = (MainActivity) (getActivity());
                    if (m.allowsNotification(oven)) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                .setContentTitle(getContext().getString(R.string.notification_title))
                                .setContentText(getContext().getString(R.string.notification_text_before) + oven.name + getContext().getString(R.string.notification_text_after))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                        notificationManager.notify(0, mBuilder.build());

                    }
                }
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
                View parent = (View) v.getParent().getParent().getParent().getParent().getParent();
                TextView t = parent.findViewById(R.id.oven_title);
                Device d = ((MainActivity)getActivity()).getDevicesMap().get(t.getText());
                API.RemoveDevice(d);
                ((MainActivity)getActivity()).externalSetFragment("homeFragment");
            }
        });
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getContext().getString(R.string.channel_name), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
