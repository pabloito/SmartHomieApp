package smartcompany.smarthomie;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private DevicesFragment devicesFragment;
    private RoutinesFragment routinesFragment;
    private SettingsFragment settingsFragment;
    private HelpFragment helpFragment;
    private NewDeviceFragment newDeviceFragment;
    private NewRoutineFragment newRoutineFragment;

    //---------- ACA ESTA TODA LA DATA QUE LEE DE LA API-----
    // Se actualiza con las funciones updateDevices y updateRoutines
    HashMap<String, Device> devicesMap;
    HashMap<String, Routine> routinesMap;
    //-------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        devicesFragment = new DevicesFragment();
        routinesFragment = new RoutinesFragment();
        settingsFragment = new SettingsFragment();
        helpFragment = new HelpFragment();
        newDeviceFragment = new NewDeviceFragment();
        newRoutineFragment = new NewRoutineFragment();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        setUpHomeFragment();
                        break;
                    case R.id.nav_devices:
                        setUpDevicesFragment();
                        break;
                    case R.id.nav_routines:
                        setUpRoutinesFragment();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                setUpSettingsFragment();
                return true;

            case R.id.action_help:
                setUpHelpFragment();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.action_bar, menu );
        return true;
    }

    private void setUpHomeFragment(){
        setFragment(homeFragment);
    }

    private void setUpDevicesFragment(){
        setFragment(devicesFragment);
    }

    private void setUpRoutinesFragment(){
        setFragment(routinesFragment);
    }

    private void setUpSettingsFragment(){
        setFragment(settingsFragment);
    }

    private void setUpHelpFragment(){
        setFragment(helpFragment);
    }

    private void setUpNewDeviceFragment(){
        setFragment(newDeviceFragment);
    }

    private void setUpNewRoutineFragment(){
        setFragment(newRoutineFragment);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void externalSetFragment(String fragment) {
        switch (fragment) {
            case "homeFragment":
                setUpHomeFragment();
                break;
            case "devicesFragment":
                setUpDevicesFragment();
                break;
            case "routinesFragment":
                setUpRoutinesFragment();
                break;
            case "settingsFragment":
                setUpSettingsFragment();
                break;
            case "helpFragment":
                setUpSettingsFragment();
                break;
            case "newDeviceFragment":
                setUpNewDeviceFragment();
                break;
            case "newRoutineFragment":
                setUpNewRoutineFragment();
                break;
        }
    }

    public void updateDevices(){
        devicesMap = new HashMap<>();

        // placeholer @nacho
        devicesMap.put("Freezer del quincho",new Device("Freezer del quincho","Heladera"));
        devicesMap.put("Freezer2",new Device("Freezer2","Heladera"));
        devicesMap.put("Freezer3",new Device("Freezer3","Heladera"));
        devicesMap.put("Freezer5",new Device("Freezer5","Heladera"));
        devicesMap.put("Fre3ezer5",new Device("Freezer5","Heladera"));
        devicesMap.put("Free5zer5",new Device("Freezer5","Heladera"));
        devicesMap.put("Free4",new Device("Freezer5","Heladera"));
        devicesMap.put("Fr2zer5",new Device("Freezer5","Heladera"));
    }

    public void updateRoutines(){
        routinesMap = new HashMap<>();

        // placeholder @nacho
        routinesMap.put("Me voy de aca",new Routine("Me voy de aca"));
        routinesMap.put("Prender freezer",new Routine("Prender freezer"));
        routinesMap.put("Prend234",new Routine("Prender freezer"));
        routinesMap.put("Prender 2er",new Routine("Prender freezer"));
        routinesMap.put("Pren4r5er",new Routine("Prender freezer"));
        routinesMap.put("Pr2eezer",new Routine("Prender freezer"));
        routinesMap.put("Pr34reezer",new Routine("Prender freezer"));
        routinesMap.put("Pren3zer",new Routine("Prender freezer"));
    }

    public HashMap<String, Device> getDevicesMap(){
        return devicesMap;
    }

    public HashMap<String, Routine> getRoutinesMap(){
        return routinesMap;
    }
}
