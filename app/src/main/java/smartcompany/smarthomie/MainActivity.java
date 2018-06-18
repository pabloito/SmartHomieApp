package smartcompany.smarthomie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private boolean comesFromRoutine = false;

    private static final int MY_NOTIFICATION_ID = 1;

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private DevicesFragment devicesFragment;
    private RoutinesFragment routinesFragment;
    private SettingsFragment settingsFragment;
    private HelpFragment helpFragment;
    private NewDeviceFragment newDeviceFragment;
    private NewRoutineFragment newRoutineFragment;
    private CurtainFragment curtainFragment;
    private DoorFragment doorFragment;
    private OvenFragment ovenFragment;
    private LightFragment lightFragment;
    private FridgeFragment fridgeFragment;
    private RoutineFragment routineFragment;

    private Device currentDevice = null;
    private Routine currentRoutine = null;

    //---------- ACA ESTA TODA LA DATA QUE LEE DE LA API-----
    // Se actualiza con las funciones updateDevices y updateRoutines
    //-------------------------------------------------------
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
        routineFragment = new RoutineFragment();

        curtainFragment = new CurtainFragment();
        fridgeFragment = new FridgeFragment();
        lightFragment = new LightFragment();
        ovenFragment = new OvenFragment();
        doorFragment = new DoorFragment();

        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
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

    private void setUpCurtainFragment(){
        setFragment(curtainFragment);
    }


    private void setFragment(Fragment fragment){
        if(getSupportFragmentManager().findFragmentByTag("current") instanceof RoutineFragment){
            Log.d("W","Comes from routine...");
            comesFromRoutine=true;
        }
        else {
            Log.d("W","Doesn't come from routine...");
            comesFromRoutine=false;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, "current");
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
            case "routineFragment":
                setFragment(routineFragment);
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
            case "curtainFragment":
                setUpCurtainFragment();
                break;
            case "fridgeFragment":
                setFragment(fridgeFragment);
                break;
            case "ovenFragment":
                setFragment(ovenFragment);
                break;
            case "doorFragment":
                setFragment(doorFragment);
                break;
            case "lightFragment":
                setFragment(lightFragment);
                break;
        }
    }

    public void updateDevices(){
        devicesMap = new HashMap<>();

        // placeholer @nacho
        devicesMap.put("Cortina del quincho",new Curtain("Cortina del quincho","Curtain"));
        devicesMap.put("freezer",new Fridge("freezer","Fridge", getApplicationContext()));
        devicesMap.put("hornito ",new Oven("hornito ","Oven", getApplicationContext()));
        devicesMap.put("luz del pasillo",new Light("luz del pasillo","Light", getApplicationContext()));
        devicesMap.put("puertita",new Door("puertita","Door"));
    }

    public void updateRoutines(){
        routinesMap = new HashMap<>();

        // placeholder @nacho
        routinesMap.put("Me voy de aca",new Routine("Me voy de aca", getApplicationContext()));
        routinesMap.put("Prender freezer",new Routine("Prender freezer", getApplicationContext()));
    }

    public HashMap<String, Device> getDevicesMap(){
        return devicesMap;
    }

    public HashMap<String, Routine> getRoutinesMap(){
        return routinesMap;
    }

    public Device getCurrentDevice(){
        return currentDevice;
    }

    public Routine getCurrentRoutine(){
        return currentRoutine;
    }

    public void setCurrentDevice(Device d){
        currentDevice=d;
    }

    public void setCurrentRoutine(Routine d){
        currentRoutine=d;
    }

    public boolean getComesFromRoutine(){
        return comesFromRoutine;
    }

}
