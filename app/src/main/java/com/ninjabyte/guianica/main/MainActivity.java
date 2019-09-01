package com.ninjabyte.guianica.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.ninjabyte.guianica.LocalFragment;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationViewEx bottomNavigationViewEx;
    private final int CONTAINER_FRAGMENT =  R.id.container_activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onCreateNavigationViewEx();

        if (Utilities.getStateConnection()){
            HomeFragment homeFragment = new HomeFragment();
            Utilities.setFragment(homeFragment, MainActivity.this, CONTAINER_FRAGMENT);
        }else {
            LocalFragment localFragment = new LocalFragment();
            Utilities.setFragment(localFragment, MainActivity.this, CONTAINER_FRAGMENT);
        }
    }

    private void onCreateNavigationViewEx(){
        bottomNavigationViewEx = findViewById(R.id.navigation_view_activity_main);
        bottomNavigationViewEx
                .enableAnimation(false)
                .enableShiftingMode(false)
                .enableItemShiftingMode(false);
    }
}
