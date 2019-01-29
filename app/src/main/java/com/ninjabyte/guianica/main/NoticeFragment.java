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

public class NoticeFragment extends Fragment {
    private Context context;
    private TextView noticeTitle;
    private String title;
    private ImageView noticeImage;
    private String urlImage;

    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        if (getArguments() != null) {
            title = getArguments().getString("title");
            urlImage = getArguments().getString("urlImage");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        noticeTitle = view.findViewById(R.id.title_fragment_notice);
        noticeImage = view.findViewById(R.id.image_fragment_notice);


       // onCreateNotice();

        return view;
    }

    private void onCreateNotice(){
        Utilities.setImageFromUrl(context, Utilities.TYPE_NORMAL, null, noticeImage, urlImage);
        noticeTitle.setText(title);
    }

}
