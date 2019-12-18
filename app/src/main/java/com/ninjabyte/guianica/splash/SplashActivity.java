package com.ninjabyte.guianica.splash;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.database.GuianicaDbHelper;
import com.ninjabyte.guianica.interfaces.buttonDialogListener;
import com.ninjabyte.guianica.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements buttonDialogListener {
    private static final int FRAGMENT_CONTAINER = R.id.fragment_main_activity_splash;
    private FirebaseAuth currentUser;
    private SharedPreferences authIsCompletePreferences;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Utilities.checkApiLevel(SplashActivity.this);
        currentUser = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser.getCurrentUser() != null ){
            if (Utilities.userFinishedAuth(SplashActivity.this)){
                Utilities.setFragment(new WelcomeFragment(), SplashActivity.this, FRAGMENT_CONTAINER);
            }else {
                Utilities.setFragment(new AuthFragment(), SplashActivity.this, FRAGMENT_CONTAINER);
            }
        }else {
            Utilities.setFragment(new AuthFragment(), SplashActivity.this, FRAGMENT_CONTAINER);
        }
    }


    @Override
    public void onClickPositiveButton(DialogInterface dialog) {
    }

    @Override
    public void onClickNegativeButton(DialogInterface dialog) {
        finish();
    }
}
