package smartcompany.smarthomie;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        devicesFragment = new DevicesFragment();
        routinesFragment = new RoutinesFragment();

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

    private void setUpHomeFragment(){
        HorizontalScrollView devices = findViewById(R.id.home_devices);
        HorizontalScrollView routines = findViewById(R.id.home_routines);

        setFragment(homeFragment);
    }

    private void setUpDevicesFragment(){
        setFragment(devicesFragment);
    }

    private void setUpRoutinesFragment(){
        setFragment(routinesFragment);
    }


    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
