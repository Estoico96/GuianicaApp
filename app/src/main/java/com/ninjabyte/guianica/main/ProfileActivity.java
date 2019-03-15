package com.ninjabyte.guianica.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.GalleryAdapter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Bundle bundle;

    private ImageButton buttonCall;
    private ImageButton buttonLocation;
    private ImageButton buttonEmail;

    private CircleImageView companyLogo;
    private TextView companyName;
    private TextView companySpecialty;

    private RecyclerView recyclerGallery;
    private GalleryAdapter galleryAdapter;

    private ArrayList<String> arrayUrlImgages;

    private DatabaseReference galleryDataReferences;

    private ValueEventListener galleryValueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bundle = getIntent().getExtras();
        Utilities.checkApiLevel(ProfileActivity.this );
        arrayUrlImgages = new ArrayList<>();

        recyclerGallery = findViewById(R.id.recycler_gallery_profile_activity);
        companyLogo = findViewById(R.id.company_logo_profile_activity);
        companyName = findViewById(R.id.company_name_profile_activity);
        companySpecialty = findViewById(R.id.company_specialty_profile_activity);
        buttonCall = findViewById(R.id.btn_call_profile_activity);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerGallery.setLayoutManager(linearLayoutManager);
        recyclerGallery.setHasFixedSize(false);
        galleryAdapter = new GalleryAdapter(ProfileActivity.this, arrayUrlImgages);
        recyclerGallery.setAdapter(galleryAdapter);

        galleryValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayUrlImgages.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String urlImages = snapshot.getValue(String.class);
                    arrayUrlImgages.add(urlImages);

                }

                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        buttonCall.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (bundle != null){


            galleryDataReferences = Utilities.getDatabaseReference()
                    .child(Utilities.DB_NODE)
                    .child("companies")
                    .child("meta-data")
                    .child("4e9e-8432-258840d49798")
                    .child("gallery");


            galleryDataReferences.addValueEventListener(galleryValueEventListener);

            Utilities.setImageFromUrl(ProfileActivity.this, Utilities.TYPE_CIRCLE, companyLogo, null, bundle.getString("rsl_url_image"));
            companyName.setText(bundle.getString("rsl_name"));
            companySpecialty.setText(bundle.getString("rsl_specialty"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_call_profile_activity :
                View view = getLayoutInflater().inflate(R.layout.fragment_fragment_bottom_sheet_dialog_profile_ativity, null);
                BottomSheetDialog dialog = new BottomSheetDialog(this);
                dialog.setContentView(view);
                dialog.show();
                break;
        }
    }
}
