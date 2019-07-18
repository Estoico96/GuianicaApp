package com.ninjabyte.guianica.splash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.database.GuianicaDbHelper;
import com.ninjabyte.guianica.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int FRAGMENT_CONTAINER = R.id.fragment_main_activity_splash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Utilities.checkApiLevel(SplashActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            return;
        }
        Utilities.setFragment(new AuthFragment(), SplashActivity.this, FRAGMENT_CONTAINER);
    }


}
