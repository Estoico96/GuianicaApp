package com.ninjabyte.guianica.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.CategoryHomefragmentAdapter;
import com.ninjabyte.guianica.adapters.CommercialAdapter;
import com.ninjabyte.guianica.adapters.OfferAdapter;
import com.ninjabyte.guianica.model.Category;
import com.ninjabyte.guianica.model.Commercial;
import com.ninjabyte.guianica.model.Offer;
import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private Context context;
    private Activity activity;
    private CircleImageView userImage;
    private TextView userName;

    private ViewPager viewPagerCommercial;

    private CommercialAdapter commercialAdapter;
    private OfferAdapter adapterOffer;
    private CategoryHomefragmentAdapter adapterCategory;

    private ArrayList<Offer> arrayOffers;
    private ArrayList<Category> arrayCategory;
    private ArrayList<Commercial> arrayCommercials;

    private DatabaseReference offerDatabaseReference;
    private DatabaseReference categoryDatabaseReference;
    private DatabaseReference noticeDatabaseReference;

    private ValueEventListener offerValueEventListener;
    private ValueEventListener noticeValueEventListener;
    private ChildEventListener categoryChildEventListener;

    private TextView tooltipCategory;
    private TextView tooltipOffer;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();

        Utilities.checkApiLevel(activity);

        onCreateOffer();
        onCreateCategory();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentHomeView = inflater.inflate(R.layout.fragment_home, container, false);

        userImage = fragmentHomeView.findViewById(R.id.user_image_fragment_home);
        userName = fragmentHomeView.findViewById(R.id.say_hello_home_fragment);
        RecyclerView recyclerViewOffer = fragmentHomeView.findViewById(R.id.recycler_offers_home_fragment);
        RecyclerView recyclerViewCategories = fragmentHomeView.findViewById(R.id.recycler_category_home_fragment);
        viewPagerCommercial = fragmentHomeView.findViewById(R.id.viewpager_commercial_home_fragment);
        tooltipOffer = fragmentHomeView.findViewById(R.id.tooltip_offer_home_fragment);
        tooltipCategory = fragmentHomeView.findViewById(R.id.tooltip_category_home_fragment);


        LinearLayoutManager linearLayoutManagerOffer = new LinearLayoutManager(context);
        linearLayoutManagerOffer.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager gridLayoutManagerCategory = new GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false);

        LinearLayoutManager linearLayoutManagerCategory = new LinearLayoutManager(context);
        linearLayoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL);


        // OFFERS
        recyclerViewOffer.setLayoutManager(linearLayoutManagerOffer);
        recyclerViewOffer.setHasFixedSize(false);
        adapterOffer = new OfferAdapter(context, arrayOffers);
        recyclerViewOffer.setAdapter(adapterOffer);

        //CATEGORIES
        recyclerViewCategories.setLayoutManager(gridLayoutManagerCategory);
        recyclerViewCategories.setHasFixedSize(false);
        adapterCategory = new CategoryHomefragmentAdapter(context, arrayCategory);
        recyclerViewCategories.setAdapter(adapterCategory);

        //NOTICES
        onCreateCommercials();


        offerDatabaseReference.addValueEventListener(offerValueEventListener);
        categoryDatabaseReference.addChildEventListener(categoryChildEventListener);
        noticeDatabaseReference.addValueEventListener(noticeValueEventListener);

        onCreateDataUser();

        return fragmentHomeView;
    }


    private void onCreateOffer() {
        arrayOffers = new ArrayList<>();

        offerDatabaseReference = Utilities.getDatabaseReference()
                .child(Utilities.DB_NODE)
                .child("offers")
                .child("data");

        offerValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayOffers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Offer offer = snapshot.getValue(Offer.class);
                    arrayOffers.add(offer);
                }
                adapterOffer.notifyDataSetChanged();
                String offerCount = arrayOffers.size() + " negocios";
                tooltipOffer.setText(offerCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void onCreateCategory() {
        arrayCategory = new ArrayList<>();

        categoryDatabaseReference = Utilities.getDatabaseReference()
                .child("7a4fa054-4287-4e9e-8432-258840d49798")
                .child("categories");

        categoryChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Category category = dataSnapshot.getValue(Category.class);
                arrayCategory.add(category);

                adapterCategory.notifyDataSetChanged();
                tooltipCategory.setText(String.valueOf(arrayCategory.size()));
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
    }

    private void onCreateCommercials() {
        arrayCommercials = new ArrayList<>();

        noticeDatabaseReference = Utilities.getDatabaseReference()
                .child("7a4fa054-4287-4e9e-8432-258840d49798")
                .child("commercial")
                .child("data");

        noticeValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Commercial commercial = snapshot.getValue(Commercial.class);
                    arrayCommercials.add(commercial);
                }

                commercialAdapter = new CommercialAdapter(context, activity,arrayCommercials);
                viewPagerCommercial.setAdapter(commercialAdapter);
                viewPagerCommercial.setCurrentItem(Utilities.getCommercialInt(context, arrayCommercials.size()));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
    }

    private void onCreateDataUser(){
        String[] firstUserName =  Utilities.getCurrentUser("displayName").split(" ", 2);
        userName.setText(String.format(getResources().getString(R.string.say_hello_home_fragment), firstUserName[0]));
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        Utilities.setImageFromUrl(context, Utilities.TYPE_CIRCLE, userImage,
                null, Utilities.getCurrentUser("photoUrl"));
    }
}
