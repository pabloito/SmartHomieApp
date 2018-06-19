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
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurtainFragment extends Fragment {
    private static final int MY_NOTIFICATION_ID = 1;
    Curtain curtain;
    Routine routine;

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

        MainActivity mainActivity = (MainActivity)getActivity();
        curtain = (Curtain) mainActivity.getCurrentDevice();

        if (mainActivity.getComesFromRoutine()) {
            routine = mainActivity.getCurrentRoutine();
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

        final Button curtainButton = (Button) view.findViewById(R.id.curtain_button);
        final TextView curtainText= (TextView) view.findViewById(R.id.curtain_text);
        final Button removeButton = (Button) view.findViewById(R.id.curtain_remove_button);

        if(curtain.isRaised()){
            curtainButton.setText(R.string.curtain_button_off);
            curtainText.setText(R.string.curtain_text_off);
        }else{
            curtainButton.setText(R.string.curtain_button_on);
            curtainText.setText(R.string.curtain_text_on);
        }


        // HANDLERS DIFERENCIADOS
        if(mainActivity.getComesFromRoutine()){
            curtainButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String str = getResources().getString(R.string.curtain_button_off);
                    if(curtainButton.getText().equals(str)){ //TURINING ON CASE
                        curtainButton.setText(R.string.curtain_button_on);
                        curtainText.setText(R.string.curtain_text_on);
                        routine.actions.add(new RoutineAction(curtain.id,"down",null));
                    }
                    else{ //TURNING OFF CASE
                        curtainButton.setText(R.string.curtain_button_off);
                        curtainText.setText(R.string.curtain_text_off);
                        routine.actions.add(new RoutineAction(curtain.id,"up",null));
                    }
                }
            });
        }
        else{
            curtainButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    String str = getResources().getString(R.string.curtain_button_off);


                    createNotificationChannel();

                    MainActivity m = (MainActivity) (getActivity());
                    if(m.allowsNotification(curtain)) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                                .setSmallIcon(R.drawable.baseline_home_black_24dp)
                                .setContentTitle(getContext().getString(R.string.notification_title))
                                .setContentText(getContext().getString(R.string.notification_text_before)+curtain.name+getContext().getString(R.string.notification_text_after))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                        notificationManager.notify(0, mBuilder.build());
                    }

                    if(curtainButton.getText().equals(str)){ //TURINING ON CASE
                        curtainButton.setText(R.string.curtain_button_on);
                        curtainText.setText(R.string.curtain_text_on);
                        curtain.lowerCurtain();
                    }
                    else{ //TURNING OFF CASE
                        curtainButton.setText(R.string.curtain_button_off);
                        curtainText.setText(R.string.curtain_text_off);
                        curtain.raiseCurtain();
                    }
                }
            });
        }


        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                View parent = (View) v.getParent().getParent().getParent().getParent().getParent();
                TextView t = parent.findViewById(R.id.curtain_title);
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
