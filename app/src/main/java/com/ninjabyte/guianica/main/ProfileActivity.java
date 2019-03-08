package com.ninjabyte.guianica.main;

import android.support.v7.app.AppCompatActivity;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;

import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {
    private NavigationTabStrip navigationTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Utilities.checkApiLevel(ProfileActivity.this );

        navigationTabStrip  = (NavigationTabStrip) findViewById(R.id.tabs_strip_profile_activity);
        navigationTabStrip.setTitles("Telefonos", "Direccion", "Contacto");
    }
}
