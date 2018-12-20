package com.ninjabyte.guianica.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.splash.SplashActivity;

public class HomeFragment extends Fragment {

    private Context context;
    private Activity activity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Utilities.checkApiLevel(activity);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
