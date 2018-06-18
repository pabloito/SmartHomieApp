package smartcompany.smarthomie;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

            Intent notificationIntent = new Intent(getContext(), MainActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(notificationIntent);

            final PendingIntent contentIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            back.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ((MainActivity)getActivity()).externalSetFragment("routineFragment");

                    Notification notification = new Notification.Builder(getContext())
                            .setContentTitle(getResources().getString(R.string.notification_title))
                            .setContentText(getResources().getString(R.string.notification_text))
                            .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.stat_sys_download_done))
                            .setSmallIcon(android.R.drawable.stat_sys_download_done)
                            .setContentIntent(contentIntent).build();

                    NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

                    // Ignore deprecated warning. In newer devices SDK 16+ should use build() method.
                    // getNotification() method internally calls build() method.
                    notificationManager.notify(MY_NOTIFICATION_ID, notification);

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


}
