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
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private DevicesFragment devicesFragment;
    private RoutinesFragment routinesFragment;
    private SettingsFragment settingsFragment;
    private HelpFragment helpFragment;


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

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
