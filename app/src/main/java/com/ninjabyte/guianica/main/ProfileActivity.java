package com.ninjabyte.guianica.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ninjabyte.guianica.interfaces.buttonDialogListener;
import com.ninjabyte.guianica.model.FeaturedProduct;
import com.ninjabyte.guianica.model.Offer;
import com.ninjabyte.guianica.model.SocialNetworks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, buttonDialogListener {

    private Bundle bundle;

    private static String companyUID;
    private static String companyOffersUID;

    private ImageButton buttonEmail;
    private TextView buttonSeeMoreOffers;

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
    private DatabaseReference profileOffersDatabaseReference;
    private ValueEventListener profileDataValueEventListener;
    private ValueEventListener profileDataOfferValueEventListener;

    //ELEMENTOS INICIALES
    private AlertDialog.Builder builder;

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
        if (bundle != null && profileDataValueEventListener != null && profileDataOfferValueEventListener != null) {
            if (bundle.getString("rsl_uid") != null) companyUID = bundle.getString("rsl_uid");

            initializeDefaultDataProfile();
            onCreateDataReference(bundle.getBoolean("rsl_is_offer"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call_profile_activity:
                new ContactSheetHelper(ProfileActivity.this, ProfileActivity.this, ContactSheetHelper.TYPE_TELEPHONE, contactSnapshot, "Telefonos");
                break;

            case R.id.btn_place_profile_activity:
                new ContactSheetHelper(ProfileActivity.this, ProfileActivity.this, ContactSheetHelper.TYPE_LOCATION, contactSnapshot, "Direcciones");

                break;

            case R.id.btn_facebook_activity_profile:

                dialogSocialNetworks("Facebook",
                        String.format(getResources()
                                .getString(R.string.text_content_facebook_activity_profile),bundle.getString("rsl_name")),
                        R.id.btn_facebook_activity_profile);
                /*
                Utilities.showDynamicDialog(ProfileActivity.this, this, "Facebook",
                        String.format(getResources().getString(R.string.text_content_facebook_activity_profile), bundle.getString("rsl_name"))).show();


*/
                break;

            case R.id.btn_instagram_activity_profile:
                Utilities.showDynamicDialog(ProfileActivity.this, this, "Instagram",
                        String.format(getResources().getString(R.string.text_content_facebook_activity_profile), bundle.getString("rsl_name")));

                break;


            case R.id.btn_more_offer_profile_activity:
                Intent intent = new Intent(this, DetailOfferActivity.class);
                Bundle data = new Bundle();

                data.putString("uidCmpny", bundle.getString("rsl_uid"));
                data.putString("nameCmpny", bundle.getString("rsl_name"));
                data.putString("logoCmpny", bundle.getString("rsl_url_image"));
                data.putString("uidOft", companyOffersUID);
                intent.putExtras(data);
                startActivity(intent);
                break;

        }
    }


    private void initializeDefaultDataProfile() {
        Utilities.setImageFromUrl(ProfileActivity.this, Utilities.TYPE_CIRCLE, companyLogo, null, bundle.getString("rsl_url_image"));
        companyName.setText(bundle.getString("rsl_name"));
        companySpecialty.setText(bundle.getString("rsl_specialty"));
    }


    private void onCreateDataReference(boolean isOffer) {
        DatabaseReference profileDatabaseReference = Utilities.getDatabaseReference()
                .child(Utilities.DB_NODE)
                .child("companies")
                .child("meta-data")
                .child(companyUID);
        if (isOffer) {
            profileOffersDatabaseReference = Utilities.getDatabaseReference()
                    .child(Utilities.DB_NODE)
                    .child("offers")
                    .child("data")
                    .child(companyUID);
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
        buttonSeeMoreOffers = findViewById(R.id.btn_more_offer_profile_activity);

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
        buttonSeeMoreOffers.setOnClickListener(this);


        //INICIALIZACION DE ELEMENTOS NECESAIOS
        builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
    }

    private void initializeArrays() {
        arrayUrlImages = new ArrayList<>();
        arrayFeaturedProducts = new ArrayList<>();
    }

    private void initializeListeners() {
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
                companyOffersUID = offer.getOfferID();
                initializeOffers(offer.getLastBannerUrlOffer(), offer.getCounter());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }


    private void initializeGallery(DataSnapshot snapshot) {

        arrayUrlImages.clear();

        for (DataSnapshot snap : snapshot.getChildren()) {
            String url = snap.getValue(String.class);
            arrayUrlImages.add(url);
        }

        galleryAdapter.notifyDataSetChanged();
    }

    private void initializeFeaturedProducts(DataSnapshot snapshot) {
        arrayFeaturedProducts.clear();

        for (DataSnapshot snap : snapshot.getChildren()) {
            FeaturedProduct product = snap.getValue(FeaturedProduct.class);
            arrayFeaturedProducts.add(product);
        }

        featuredProductsAdapter.notifyDataSetChanged();
    }

    private void initializeSocialNetworks(DataSnapshot snapshot) {
        mapSocialNetworks = new HashMap<>();
        for (DataSnapshot snap : snapshot.getChildren()) {
            SocialNetworks social = snap.getValue(SocialNetworks.class);
            mapSocialNetworks.put(social.getName(), social);

            switch (social.getName() != null ? social.getName() : "") {
                case "Twitter":
                    iTwitter.setVisibility(View.VISIBLE);
                    break;

                case "Facebook":
                    iFacebook.setVisibility(View.VISIBLE);
                    break;

                case "Instagram":
                    iInstagram.setVisibility(View.VISIBLE);
                    break;

                case "Youtube":
                    iYoutube.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    private void initializeOffers(String url, int count) {
        titleOffer.setVisibility(View.VISIBLE);
        tootipOffer.setVisibility(View.VISIBLE);
        bannerOffers.setVisibility(View.VISIBLE);
        tootipOffer.setText(String.format(getResources().getString(R.string.text_template_tooltip), String.valueOf(count)));
        Utilities.setImageFromUrl(ProfileActivity.this, Utilities.TYPE_NORMAL, null, bannerOffers, url);
    }

    private void dialogSocialNetworks(String title, String content, final int onClickButton) {
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (onClickButton) {
                            case R.id.btn_facebook_activity_profile:
                                String facebookId = "fb://page/1491096557677909";
                                String urlPage = "https://www.facebook.com/atomic.nicaragua/";

                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId)));
                                } catch (Exception e) {
                                    //Abre url de pagina.
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                                }
                                break;

                            case R.id.btn_instagram_activity_profile:

                                break;

                            default:


                                break;
                        }
                    }
                });

        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onClickPositiveButton(DialogInterface dialog) {

    }

    @Override
    public void onClickNegativeButton(DialogInterface dialog) {

    }
}
