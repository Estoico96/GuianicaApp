package com.ninjabyte.guianica.splash;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;

public class SplashActivity extends AppCompatActivity {
    private static final int FRAGMENT_CONTAINER = R.id.fragment_main;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkApiLevel();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            Utilities.setFragment(new ConfirmFragment(), SplashActivity.this, FRAGMENT_CONTAINER);


            return;
        }

        AuthFragment authFragment = new AuthFragment();
        Utilities.setFragment(authFragment, SplashActivity.this, FRAGMENT_CONTAINER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }

    private void checkApiLevel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            return;
        }
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#D5D5D5"));
    }
}
