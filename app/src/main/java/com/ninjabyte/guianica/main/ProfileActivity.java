package com.ninjabyte.guianica.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.CreateButtonSheetHelper;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.ContactRecyclerAdapter;
import com.ninjabyte.guianica.adapters.FeaturedProductsAdapter;
import com.ninjabyte.guianica.adapters.GalleryAdapter;
import com.ninjabyte.guianica.adapters.OfferAdapter;
import com.ninjabyte.guianica.model.FeaturedProduct;
import com.ninjabyte.guianica.model.Offer;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Bundle bundle;

    private TextView tootipOffer;

    private ImageButton buttonEmail;

    private CircleImageView companyLogo;
    private TextView companyName;
    private TextView companySpecialty;

    private ImageView lastOfferBanner;

    private GalleryAdapter galleryAdapter;
    private FeaturedProductsAdapter featuredProductsAdapter;



    private ArrayList<String> arrayUrlImages;
    private ArrayList<FeaturedProduct> arrayFeaturedProducts;

    private ValueEventListener galleryValueEventListener;
    private ValueEventListener featuredProductsValueEventListener;
    private ValueEventListener lastOfferValueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        arrayUrlImages = new ArrayList<>();
        arrayFeaturedProducts = new ArrayList<>();

        bundle = getIntent().getExtras();
        Utilities.checkApiLevel(ProfileActivity.this);

        RecyclerView recyclerGallery = findViewById(R.id.recycler_gallery_profile_activity);
        RecyclerView recyclerFeaturedProduct = findViewById(R.id.recycler_featured_products_profile_activity);
        companyLogo = findViewById(R.id.company_logo_profile_activity);
        companyName = findViewById(R.id.company_name_profile_activity);
        tootipOffer = findViewById(R.id.tooltip_offer_activity_profile);
        companySpecialty = findViewById(R.id.company_specialty_profile_activity);
        ImageButton buttonCall = findViewById(R.id.btn_call_profile_activity);
        lastOfferBanner = findViewById(R.id.last_offer_profile_activity);
        ImageButton buttonLocation = findViewById(R.id.btn_place_profile_activity);



        //RECYCLER GALLERY
        LinearLayoutManager galleryLinearLayoutManager = new LinearLayoutManager(this);
        galleryLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerGallery.setLayoutManager(galleryLinearLayoutManager);

        recyclerGallery.setHasFixedSize(false);
        galleryAdapter = new GalleryAdapter(ProfileActivity.this, arrayUrlImages);
        recyclerGallery.setAdapter(galleryAdapter);


        //RECYCLER FEATURED PRODUCTS
        LinearLayoutManager featuredProductsLinearLayoutManager = new LinearLayoutManager(this);
        featuredProductsLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerFeaturedProduct.setLayoutManager(featuredProductsLinearLayoutManager);

        recyclerFeaturedProduct.setHasFixedSize(false);
        featuredProductsAdapter = new FeaturedProductsAdapter(ProfileActivity.this, arrayFeaturedProducts);
        recyclerFeaturedProduct.setAdapter(featuredProductsAdapter);

        galleryValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayUrlImages.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String urls = snapshot.getValue(String.class);
                    arrayUrlImages.add(urls);
                }
                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        featuredProductsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayFeaturedProducts.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FeaturedProduct featuredProducts = snapshot.getValue(FeaturedProduct.class);
                    arrayFeaturedProducts.add(featuredProducts);
                }
                featuredProductsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        lastOfferValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Offer offer = dataSnapshot.getValue(Offer.class);
                Utilities.setImageFromUrl(ProfileActivity.this, Utilities.TYPE_NORMAL, null, lastOfferBanner, offer.getLastBannerUrlOffer());
                tootipOffer.setText(String.valueOf(offer.getCounter()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        buttonCall.setOnClickListener(this);
        buttonLocation.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (bundle != null) {
            initializeGallery();
            initializeProfile();
            initializeFeaturedProducts();
            initialLastOffer();
        }
    }

    @Override
    public void onClick(View v) {
        CreateButtonSheetHelper createButtonSheetHelper;

        switch (v.getId()) {
            case R.id.btn_call_profile_activity:

                createButtonSheetHelper =
                        new CreateButtonSheetHelper
                                (ProfileActivity.this, "telephones", "Teléfonos", ProfileActivity.this);
                createButtonSheetHelper
                        .getArrayTelephones();

                break;

            case R.id.btn_place_profile_activity:

                createButtonSheetHelper =
                        new CreateButtonSheetHelper
                                (ProfileActivity.this, "locations", "Sucursales", ProfileActivity.this);
                createButtonSheetHelper
                        .getArrayLocation();

                break;
        }
    }


    private void initializeGallery() {
        DatabaseReference galleryDataReferences = Utilities.getDatabaseReference()
                .child(Utilities.DB_NODE)
                .child("companies")
                .child("meta-data")
                .child("4e9e-8432-258840d49798")
                .child("gallery");

        galleryDataReferences.addValueEventListener(galleryValueEventListener);
    }

    private void initializeProfile() {
        Utilities.setImageFromUrl(ProfileActivity.this, Utilities.TYPE_CIRCLE, companyLogo, null, bundle.getString("rsl_url_image"));
        companyName.setText(bundle.getString("rsl_name"));
        companySpecialty.setText(bundle.getString("rsl_specialty"));
    }

    private void initializeFeaturedProducts(){
        DatabaseReference featuredProductsDataReferences = Utilities.getDatabaseReference()
                .child(Utilities.DB_NODE)
                .child("companies")
                .child("meta-data")
                .child("4e9e-8432-258840d49798")
                .child("featured_products");

        featuredProductsDataReferences.addValueEventListener(featuredProductsValueEventListener);
    }

    private void initialLastOffer(){
        DatabaseReference lastOfferDataReferences = Utilities.getDatabaseReference()
                .child(Utilities.DB_NODE)
                .child("offers")
                .child("data")
                .child("258840d49798");

        lastOfferDataReferences.addValueEventListener(lastOfferValueEventListener);
    }
}
