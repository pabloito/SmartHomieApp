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

    Curtain curtain;
    Routine routine;

    private static final int MY_NOTIFICATION_ID = 1;

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
        curtain = (Curtain) ((MainActivity) getActivity()).getCurrentDevice();
        MainActivity ma = (MainActivity)getActivity();

        if (ma.getComesFromRoutine()){
            routine = ma.getCurrentRoutine();
            LinearLayout routineLayout = view.findViewById(R.id.routine_lay);
            View routineView = getLayoutInflater().inflate(R.layout.routine_section_devices_layout, ((ViewGroup) getView().getParent()), false);
            routineLayout.addView(routineView);

            Button back = view.findViewById(R.id.backToRoutine);

            back.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ((MainActivity)getActivity()).externalSetFragment("routineFragment");
                }
            });
        }

        final TextView title = (TextView) view.findViewById(R.id.curtain_title);
        title.setText(curtain.getName());

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

        curtainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                createNotificationChannel();

                System.out.println("entered");

                if(curtain.allowsNotification()) {
                    System.out.println("entered");
                    Intent intent = new Intent(getContext(), Curtain.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), getContext().getString(R.string.channel_name))
                            .setSmallIcon(R.drawable.baseline_home_black_24dp)
                            .setContentTitle("Test Notification")
                            .setContentText("Test Notification")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)    
                            .setAutoCancel(true);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                    notificationManager.notify(0, mBuilder.build());
                }

                String str = getResources().getString(R.string.curtain_button_off);

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
        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast toast = Toast.makeText(getContext(), "Lo removiste!.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private NotificationChannel createNotificationChannel() {
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
            return channel;
        }
        return null;
    }

}
