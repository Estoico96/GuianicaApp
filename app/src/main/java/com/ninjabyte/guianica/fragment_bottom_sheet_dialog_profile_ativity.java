package com.ninjabyte.guianica;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class fragment_bottom_sheet_dialog_profile_ativity extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_OPTION = "option";

    private DatabaseReference conatactDatabaseReference;
    private ValueEventListener contactValueEventListener;

    private String title;
    private String option;

    public fragment_bottom_sheet_dialog_profile_ativity() {
        // Required empty public constructor
    }

    public static fragment_bottom_sheet_dialog_profile_ativity newInstance(String title, String option) {
        fragment_bottom_sheet_dialog_profile_ativity fragment = new fragment_bottom_sheet_dialog_profile_ativity();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_OPTION, option);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            option = getArguments().getString(ARG_OPTION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_bottom_sheet_dialog_profile_ativity, container, false);
    }

}
