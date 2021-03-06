package smartcompany.smarthomie;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private boolean comesFromRoutine = false;

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
    HashMap<String, Device> devicesMap = new HashMap<>();
    HashMap<String, Routine> routinesMap = new HashMap<>();
    //-------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API.initAPIConnection(getApplicationContext());
        API.SendAndRequestAllDevices();
        API.SendAndRequestAllRoutines();

        mMainFrame =  findViewById(R.id.main_frame);
        mMainNav =  findViewById(R.id.main_nav);

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

        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
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

    public void onStart(){
        super.onStart();
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

    boolean comesFromDeviceThatComesFromRoutine = false;

    public boolean getComesFromDeviceThatComesFromRoutine(){
        return comesFromDeviceThatComesFromRoutine;
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
        Log.d("TAG",fragment.toString());
        Fragment frag = getSupportFragmentManager().findFragmentByTag("current");
        if((frag instanceof CurtainFragment)||(frag instanceof LightFragment)||(frag instanceof DoorFragment)||(frag instanceof OvenFragment)||(frag instanceof FridgeFragment)){
            comesFromDeviceThatComesFromRoutine=true;
        }
        else
            comesFromDeviceThatComesFromRoutine=false;

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

    public void updateDevices(){ devicesMap = API.getDevicesMap(); }

    public void updateRoutines(){ routinesMap = API.getRoutineMap(); }

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

    public boolean allowsNotification(Device d){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Log.d("NOTIF",d.name);

        int ret;
        if((ret=prefs.getInt(d.name,3))==3){
            return false;
        }
        else if (ret==0){
            Log.d("NOTIF","false");
            return false;
        }
        Log.d("NOTIF",String.valueOf(ret));
        Log.d("NOTIF","true");
        return true;
    }
}
