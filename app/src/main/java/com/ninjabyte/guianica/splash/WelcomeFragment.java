package com.ninjabyte.guianica.splash;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.interfaces.buttonDialogListener;
import com.ninjabyte.guianica.main.MainActivity;
import com.ninjabyte.guianica.model.Connection;


    public class WelcomeFragment extends Fragment implements buttonDialogListener {

    private Context context;
    private Activity activity;
    private Connection connection;
    private TextView loadingText;
    private TextView welcomeText;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();
        connection = Utilities.checkConnetion(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        welcomeText = view.findViewById(R.id.text_init_fragment_welcome);
        loadingText = view.findViewById(R.id.text_loading_fragment_welcome);

        welcomeText.setText(Utilities.getRandomProverb());

        if (connection.isConnection()){
            if (connection.isWifi()){
                loadingText.setText(getResources().getText(R.string.text_checking_updates_fragment_welcome));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(context, MainActivity.class));
                    }
                }, 7000);

            }else {
                Utilities.showDialog(context, this, R.string.text_title_warning_connection_fragment_welcome,
                        R.string.text_content_warning_connection_mobile_fragment_welcome,
                        "continuar", "salir");

            }
        }else {
            Utilities.showDialog(context, this, R.string.text_title_warning_connection_fragment_welcome,
                    R.string.text_content_no_connection_fragment_welcome,
                    "continuar", "salir");
        }

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onClickPositiveButton(DialogInterface dialog) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, MainActivity.class));
                activity.finish();
            }
        }, 7000);

    }

    @Override
    public void onClickNegativeButton(DialogInterface dialog) {
        dialog.cancel();
       activity.finish();
    }
}
