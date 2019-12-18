package com.ninjabyte.guianica.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.ContactSheetHelper;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.FeaturedProductsAdapter;
import com.ninjabyte.guianica.adapters.GalleryAdapter;
import com.ninjabyte.guianica.model.FeaturedProduct;
import com.ninjabyte.guianica.model.Offer;
import com.ninjabyte.guianica.model.SocialNetworks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Bundle bundle;
    private String urlLastbannerOffers;
    private ImageButton buttonEmail;
    private CircleImageView companyLogo;
    private TextView companyName;
    private TextView titleOffer;
    private TextView tootipOffer;
    private TextView companySpecialty;
    private ImageView iFacebook;
    private ImageView iTwitter;
    private ImageView iInstagram;
    private ImageView iYoutube;
    private ImageView bannerOffers;
    private Map<String, SocialNetworks> mapSocialNetworks;
    private GalleryAdapter galleryAdapter;
    private FeaturedProductsAdapter featuredProductsAdapter;


    private ArrayList<String> arrayUrlImages;
    private ArrayList<FeaturedProduct> arrayFeaturedProducts;

    private DataSnapshot contactSnapshot;
    private  DatabaseReference profileOffersDatabaseReference;
    private ValueEventListener profileDataValueEventListener;
    private ValueEventListener profileDataOfferValueEventListener;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bundle = getIntent().getExtras();
        Utilities.checkApiLevel(ProfileActivity.this);

        initializeArrays();
        bindViews();
        initializeListeners();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (bundle != null && profileDataValueEventListener != null && profileDataOfferValueEventListener != null)  {
            if (bundle.getString("rsl_uid") != null) urlLastbannerOffers = bundle.getString("rsl_uid");
            if (bundle.get("oftKey") != null){

                return;
            }
            initializeDefaultDataProfile();
            onCreateDataReference();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call_profile_activity:
                new ContactSheetHelper(ProfileActivity.this, ProfileActivity.this, ContactSheetHelper.TYPE_TELEPHONE, contactSnapshot);
                break;

            case R.id.btn_place_profile_activity:
                new ContactSheetHelper(ProfileActivity.this, ProfileActivity.this, ContactSheetHelper.TYPE_LOCATION, contactSnapshot);

                break;

            case R.id.btn_facebook_activity_profile :

                new SimpleTooltip.Builder(this)
                        .anchorView(v)
                        .text(mapSocialNetworks.get("Facebook").getUser())
                        .gravity(Gravity.TOP)
                        .build()
                        .show();


                break;


        }
    }


    private void initializeDefaultDataProfile() {
        Utilities.setImageFromUrl(ProfileActivity.this, Utilities.TYPE_CIRCLE, companyLogo, null, bundle.getString("rsl_url_image"));
        companyName.setText(bundle.getString("rsl_name"));
        companySpecialty.setText(bundle.getString("rsl_specialty"));
    }


    private void onCreateDataReference() {
        DatabaseReference profileDatabaseReference = Utilities.getDatabaseReference()
                .child(Utilities.DB_NODE)
                .child("companies")
                .child("meta-data")
                .child("4e9e-8432-258840d49798");

        if (urlLastbannerOffers != null){
            profileOffersDatabaseReference = Utilities.getDatabaseReference()
                    .child(Utilities.DB_NODE)
                    .child("offers")
                    .child("data")
                    .child(urlLastbannerOffers);

            profileOffersDatabaseReference.addListenerForSingleValueEvent(profileDataOfferValueEventListener);
        }

        profileDatabaseReference.addListenerForSingleValueEvent(profileDataValueEventListener);

    }


    private void bindViews() {

        RecyclerView recyclerGallery = findViewById(R.id.recycler_gallery_profile_activity);
        RecyclerView recyclerFeaturedProduct = findViewById(R.id.recycler_featured_products_profile_activity);

        companyLogo = findViewById(R.id.company_logo_profile_activity);
        companyName = findViewById(R.id.company_name_profile_activity);
        companySpecialty = findViewById(R.id.company_specialty_profile_activity);
        iFacebook = findViewById(R.id.btn_facebook_activity_profile);
        iTwitter = findViewById(R.id.btn_twitter_activity_profile);
        iInstagram = findViewById(R.id.btn_instagram_activity_profile);
        iYoutube = findViewById(R.id.btn_youtebe_activity_profile);
        bannerOffers = findViewById(R.id.last_banner_offer_profile_activity);
        titleOffer = findViewById(R.id.title_offers_profile_activity);
        tootipOffer = findViewById(R.id.tooltip_offers_profile_activity);

        ImageButton buttonCall = findViewById(R.id.btn_call_profile_activity);
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

        buttonCall.setOnClickListener(this);
        buttonLocation.setOnClickListener(this);
        iFacebook.setOnClickListener(this);
    }

    private void initializeArrays(){
        arrayUrlImages = new ArrayList<>();
        arrayFeaturedProducts = new ArrayList<>();
    }

    private void initializeListeners(){
        profileDataValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contactSnapshot = dataSnapshot.child("contacts");
                initializeGallery(dataSnapshot.child("gallery"));
                initializeSocialNetworks(dataSnapshot.child("contacts").child("social_networks"));
                initializeFeaturedProducts(dataSnapshot.child("featured_products"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        profileDataOfferValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Offer offer = dataSnapshot.getValue(Offer.class);
                initializeOffers(offer.getLastBannerUrlOffer(), offer.getCounter());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }


    private void initializeGallery(DataSnapshot snapshot){

        arrayUrlImages.clear();

        for (DataSnapshot snap : snapshot.getChildren()){
            String url = snap.getValue(String.class);
            arrayUrlImages.add(url);
        }

        galleryAdapter.notifyDataSetChanged();
    }

    private void initializeFeaturedProducts(DataSnapshot snapshot){
        arrayFeaturedProducts.clear();

        for (DataSnapshot snap : snapshot.getChildren()){
            FeaturedProduct product = snap.getValue(FeaturedProduct.class);
            arrayFeaturedProducts.add(product);
        }

        featuredProductsAdapter.notifyDataSetChanged();
    }
    private void initializeSocialNetworks(DataSnapshot snapshot){
        mapSocialNetworks = new HashMap <>();
        for (DataSnapshot snap : snapshot.getChildren()){
            SocialNetworks social = snap.getValue(SocialNetworks.class);
            mapSocialNetworks.put(social.getName(), social);

            switch (social.getName() != null ? social.getName() : ""){
                case "Twitter" :
                    iTwitter.setVisibility(View.VISIBLE);
                    break;

                case "Facebook" :
                    iFacebook.setVisibility(View.VISIBLE);
                    break;

                case "Instagram" :
                    iInstagram.setVisibility(View.VISIBLE);
                    break;

                case "Youtube" :
                    iYoutube.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }
    private void initializeOffers(String url, int count){
        titleOffer.setVisibility(View.VISIBLE);
        tootipOffer.setVisibility(View.VISIBLE);
        bannerOffers.setVisibility(View.VISIBLE);
        tootipOffer.setText(String.format(getResources().getString(R.string.text_template_tooltip), String.valueOf(count)));
        Utilities.setImageFromUrl(ProfileActivity.this, Utilities.TYPE_NORMAL,null,bannerOffers, url);
    }
}
