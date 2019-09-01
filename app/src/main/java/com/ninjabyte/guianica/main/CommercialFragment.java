package com.ninjabyte.guianica.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;

public class CommercialFragment extends Fragment {
    private Context context;
    private TextView companyName;
    private TextView companyDescription;
    private String name;
    private String description;
    private ImageView companyBanner;
    private String url;

    public CommercialFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        if (getArguments() != null) {
            name = getArguments().getString("title");
            description = getArguments().getString("title");
            url = getArguments().getString("urlBanner");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment_commercial, container, false);

        companyName = view.findViewById(R.id.company_name_fragment_commercial);
        companyDescription = view.findViewById(R.id.company_description_fragment_commercial);


        // onCreateNotice();

        return view;
    }

    private void onCreateNotice() {
        Utilities.setImageFromUrl(context, Utilities.TYPE_NORMAL, null, companyBanner, url);
        companyName.setText(name);
    }

}
