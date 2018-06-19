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

import java.util.LinkedList;
import java.util.List;


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

        final TextView title = (TextView) view.findViewById(R.id.light_title);
        title.setText(light.getName());

        final SeekBar lampBrightnessSlider = (SeekBar) view.findViewById(R.id.lamp_brightness_slider);
        final Spinner lampColorSelect = (Spinner) view.findViewById(R.id.lamp_color_select);
        final Spinner lampStateSelect = (Spinner) view.findViewById(R.id.lamp_state_select);
        final Button removeButton = (Button) view.findViewById(R.id.lamp_remove_button);

        lampBrightnessSlider.setProgress(light.getBrightness()-1);
        lampColorSelect.setSelection(light.getColorIndex());
        lampStateSelect.setSelection(light.getStateIndex());

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
                        MainActivity m = (MainActivity) (getActivity());
                        if(m.allowsNotification(light)) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                    .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                    .setContentTitle(getContext().getString(R.string.notification_title))
                                    .setContentText(getContext().getString(R.string.notification_text_before)+light.name+getContext().getString(R.string.notification_text_after))
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                            notificationManager.notify(0, mBuilder.build());
                        }
                        curr=seekBar.getProgress()-(seekBar.getMax()-max);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        if(mainActivity.getComesFromRoutine()){
                            List<Object> param = new LinkedList<>();
                            param.add(curr);
                            routine.actions.add(new RoutineAction(light.id,"setBrightness",param));
                        }else {
                            light.setBrightness(curr);
                            System.out.println(curr);
                        }
                    }
                });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.lamp_state_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lampStateSelect.setAdapter(adapter);
        lampColorSelect.setSelection(light.getStateIndex(),false);
        lampStateSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.lamp_state_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);
                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity.getComesFromRoutine()) {
                    if(mainActivity.getComesFromRoutine()){
                        String[] array2= API.getContext().getResources().getStringArray(R.array.lamp_state_array_L);
                        if(array2[index].equals("on")){
                            routine.actions.add(new RoutineAction(light.id,"turnOn",null));
                        }else{
                            routine.actions.add(new RoutineAction(light.id,"turnOff",null));
                        }
                    }
                }else {
                    light.setState(array[index]);
                    MainActivity m = (MainActivity) (getActivity());
                    if (m.allowsNotification(light)) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                .setContentTitle(getContext().getString(R.string.notification_title))
                                .setContentText(getContext().getString(R.string.notification_text_before) + light.name + getContext().getString(R.string.notification_text_after))
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
                light.setState(null);
            }

        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.lamp_color_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lampColorSelect.setAdapter(adapter2);
        lampColorSelect.setSelection(light.getColorIndex(),false);
        lampColorSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.lamp_color_array);
                int index = parentView.getSelectedItemPosition();
                System.out.println("you have selected: "+array[index]);
                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity.getComesFromRoutine()) {
                    List<Object> param = new LinkedList<>();

                    switch(index){
                        case 0:
                            param.add("EFF70A");
                            routine.actions.add(new RoutineAction(light.id,"setColor",param));
                            break;
                        case 1:
                            param.add("F70A22");
                            routine.actions.add(new RoutineAction(light.id,"setColor",param));
                            break;
                        case 2:
                            param.add("1E07F0");
                            routine.actions.add(new RoutineAction(light.id,"setColor",param));
                            break;
                        case 3:
                            param.add("07F04D");
                            routine.actions.add(new RoutineAction(light.id,"setColor",param));
                            break;
                    }
                }else {
                    light.setColor(array[index]);
                    MainActivity m = (MainActivity) (getActivity());
                    if (m.allowsNotification(light)) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                .setContentTitle(getContext().getString(R.string.notification_title))
                                .setContentText(getContext().getString(R.string.notification_text_before) + light.name + getContext().getString(R.string.notification_text_after))
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
                light.setColor(null);
            }

        });

        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                View parent = (View) v.getParent().getParent().getParent().getParent().getParent();
                TextView t = parent.findViewById(R.id.light_title);
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
