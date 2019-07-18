package com.ninjabyte.guianica.splash;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;

public class PrivacyPolicyWebViewActivity extends AppCompatActivity {
    private final String PRIVACY_POLICY_URL = "https://guianica-db801.firebaseapp.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy_web_view);
        Utilities.checkApiLevel(PrivacyPolicyWebViewActivity.this);
        Toolbar toolbar = findViewById(R.id.toolbar_activity_privacy_policy_web_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        WebView webView = findViewById(R.id.web_view_activity_privacy_policy);
        webView.loadUrl(PRIVACY_POLICY_URL);
    }
}
