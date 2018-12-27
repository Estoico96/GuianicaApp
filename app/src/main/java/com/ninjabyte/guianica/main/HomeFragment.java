package com.ninjabyte.guianica.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.splash.SplashActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private Context context;
    private Activity activity;
    private CircleImageView userImage;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Utilities.checkApiLevel(activity);

        View fragmentHomeView = inflater.inflate(R.layout.fragment_home, container, false);

        userImage = fragmentHomeView.findViewById(R.id.user_image_fragment_home);
        Utilities.setImageFromUrl(context, userImage, Utilities.getCurrentUser("photoUrl"));

        // Inflate the layout for this fragment
        return fragmentHomeView;
    }

}
