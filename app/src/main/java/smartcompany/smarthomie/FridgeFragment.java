package smartcompany.smarthomie;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment {

    Fridge fridge;
    Routine routine;

    public FridgeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fridge = (Fridge) ((MainActivity)getActivity()).getCurrentDevice();
        System.out.println(fridge);

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

        final TextView title = (TextView) view.findViewById(R.id.fridge_title);
        title.setText(fridge.getName());

        final SeekBar fridgeTempSlider = (SeekBar) view.findViewById(R.id.fridge_temp_slider);
        final SeekBar refridgeratorTempSlider = (SeekBar) view.findViewById(R.id.refridgerator_temp_slider);
        final Spinner fridgeModeSelect = (Spinner) view.findViewById(R.id.fridge_mode_select);
        final Button removeButton = (Button) view.findViewById(R.id.fridge_remove_button);

        fridgeTempSlider.setProgress(fridge.getFreezerTemperature()+20);
        refridgeratorTempSlider.setProgress(fridge.getRefridgeratorTemperature()-2);
        fridgeModeSelect.setSelection(fridge.getModeIndex());

        fridgeTempSlider.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int min=-20;
                    int max=-8;
                    int curr;
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser)
                    {
                        if(fridge.allowsNotification()) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                    .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                    .setContentTitle(getContext().getString(R.string.notification_title))
                                    .setContentText(getContext().getString(R.string.notification_text_before)+fridge.name+getContext().getString(R.string.notification_text_after))
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                            notificationManager.notify(0, mBuilder.build());
                        }
                        curr=seekBar.getProgress()-(seekBar.getMax()-max);
                        fridge.setFreezerTemperature(curr);
                        System.out.println(curr);
                    }
                });
        refridgeratorTempSlider.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int min=2;
                    int max=8;
                    int curr;
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser)
                    {
                        if(fridge.allowsNotification()) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                    .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                    .setContentTitle(getContext().getString(R.string.notification_title))
                                    .setContentText(getContext().getString(R.string.notification_text_before)+fridge.name+getContext().getString(R.string.notification_text_after))
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                            notificationManager.notify(0, mBuilder.build());
                        }
                        curr=seekBar.getProgress()-(seekBar.getMax()-max);
                        fridge.setRefridgeratorTemperature(curr);
                        System.out.println(curr);
                    }
                });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.fridge_mode_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fridgeModeSelect.setAdapter(adapter);
        fridgeModeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] array = getResources().getStringArray(R.array.fridge_mode_array);
                int index = parentView.getSelectedItemPosition();
                fridge.setMode(array[index]);
                System.out.println("you have selected: "+array[index]);
                if(fridge.allowsNotification()) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                            .setSmallIcon(R.drawable.baseline_home_black_24dp)
                            .setContentTitle(getContext().getString(R.string.notification_title))
                            .setContentText(getContext().getString(R.string.notification_text_before)+fridge.name+getContext().getString(R.string.notification_text_after))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                    notificationManager.notify(0, mBuilder.build());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("No item selected");
                fridge.setMode(null);
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
