package com.ninjabyte.guianica.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private BottomNavigationViewEx bottomNavigationViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationViewEx = findViewById(R.id.navigation_view_activity_main);
        onCreateNaviegationViewEx();

        homeFragment = new HomeFragment();
        Utilities.setFragment(homeFragment, MainActivity.this, R.id.container_activity_main);

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.v("asdf", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("asdf", "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("asdf", "onResume");

    }

    private void onCreateNaviegationViewEx(){
        bottomNavigationViewEx
                .enableAnimation(false)
                .enableShiftingMode(false)
                .enableItemShiftingMode(false);
    }
}
