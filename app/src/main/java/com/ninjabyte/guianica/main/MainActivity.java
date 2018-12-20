package com.ninjabyte.guianica.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();

        Utilities.setFragment(homeFragment, MainActivity.this, R.id.container_activity_main);

    }
}
