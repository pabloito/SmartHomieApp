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
        createNotificationChannel();
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

                MainActivity m = (MainActivity) (getActivity());
                if(m.getComesFromRoutine()){
                    String str = getResources().getString(R.string.door_button_off);
                    if(doorButton.getText().equals(str)){ //TURINING ON CASE
                        doorButton.setText(R.string.door_button_on);
                        doorText.setText(R.string.door_text_on);
                        routine.actions.add(new RoutineAction(door.id,"open",null));
                    }
                    else{ //TURNING OFF CASE
                        doorButton.setText(R.string.door_button_off);
                        doorText.setText(R.string.door_text_off);
                        routine.actions.add(new RoutineAction(door.id,"close",null));
                    }
                }
                else{
                    if(m.allowsNotification(door)) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                .setContentTitle(getContext().getString(R.string.notification_title))
                                .setContentText(getContext().getString(R.string.notification_text_before)+door.name+getContext().getString(R.string.notification_text_after))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                        notificationManager.notify(0, mBuilder.build());
                    }
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
                }

                System.out.println("Is locked?: "+door.isLocked()+" isClosed?: "+door.isClosed());
            }
        });
        lockButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity m = (MainActivity) (getActivity());
                if(m.getComesFromRoutine()){
                    String str = getResources().getString(R.string.lock_button_off);

                    if(lockButton.getText().equals(str)){ //TURINING ON CASE
                        lockButton.setText(R.string.lock_button_on);
                        lockText.setText(R.string.lock_text_on);
                        routine.actions.add(new RoutineAction(door.id,"unlock",null));
                    }
                    else{ //TURNING OFF CASE
                        lockButton.setText(R.string.lock_button_off);
                        lockText.setText(R.string.lock_text_off);
                        routine.actions.add(new RoutineAction(door.id,"lock",null));
                    }
                }
                else{
                    if(m.allowsNotification(door)) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                .setContentTitle(getContext().getString(R.string.notification_title))
                                .setContentText(getContext().getString(R.string.notification_text_before)+door.name+getContext().getString(R.string.notification_text_after))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                        notificationManager.notify(0, mBuilder.build());
                    }
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
                }

                System.out.println("Is locked?: "+door.isLocked()+" isClosed?: "+door.isClosed());
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                View parent = (View) v.getParent().getParent().getParent().getParent().getParent();
                TextView t = parent.findViewById(R.id.door_title);
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
