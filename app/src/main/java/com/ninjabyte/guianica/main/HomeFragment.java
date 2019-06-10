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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.CategoryHomefragmentAdapter;
import com.ninjabyte.guianica.adapters.NoticeAdapter;
import com.ninjabyte.guianica.adapters.OfferAdapter;
import com.ninjabyte.guianica.adapters.TouristPlacesAdapter;
import com.ninjabyte.guianica.model.Category;
import com.ninjabyte.guianica.model.Notice;
import com.ninjabyte.guianica.model.Offer;
import com.ninjabyte.guianica.model.TouristPlace;
import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private Context context;
    private Activity activity;
    private CircleImageView userImage;
    private TextView userName;

    private RecyclerView recyclerViewOffer;
    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewTouristPlaces;

    private ViewPager viewPagerNotice;
    private BubblePageIndicator noticeIndicator;

    private NoticeAdapter adapterNotice;
    private OfferAdapter adapterOffer;
    private CategoryHomefragmentAdapter adapterCategory;
    private TouristPlacesAdapter adapterTouristPlaces;

    private ArrayList<Offer> arrayOffers;
    private ArrayList<Category> arrayCategory;
    private ArrayList<Notice> arrayNotices;
    private ArrayList<TouristPlace> arrayTouristPlaces;

    private DatabaseReference offerDatabaseReference;
    private DatabaseReference categoryDatabaseReference;
    private DatabaseReference noticeDatabaseReference;
    private DatabaseReference touristPlacesDatabaseReference;

    private ValueEventListener offerValueEventListener;
    private ValueEventListener noticeValueEventListener;
    private ValueEventListener touristPlacesValueEventListener;
    private ChildEventListener categoryChildEventListener;
    private ChildEventListener noticeChildEventListener;

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
        onCreateTouristPlaces();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentHomeView = inflater.inflate(R.layout.fragment_home, container, false);

        userImage = fragmentHomeView.findViewById(R.id.user_image_fragment_home);
        userName = fragmentHomeView.findViewById(R.id.say_hello_home_fragment);
        recyclerViewOffer = fragmentHomeView.findViewById(R.id.recycler_offers_home_fragment);
        recyclerViewCategories = fragmentHomeView.findViewById(R.id.recycler_category_home_fragment);
        recyclerViewTouristPlaces = fragmentHomeView.findViewById(R.id.recycler_tourist_places_home_fragment);
        viewPagerNotice = fragmentHomeView.findViewById(R.id.viewpager_notice_home_fragment);
        noticeIndicator = fragmentHomeView.findViewById(R.id.notice_page_indicator_home_fragment);
        tooltipOffer = fragmentHomeView.findViewById(R.id.tooltip_offer_home_fragment);
        tooltipCategory = fragmentHomeView.findViewById(R.id.tooltip_category_home_fragment);


        LinearLayoutManager linearLayoutManagerOffer = new LinearLayoutManager(context);
        linearLayoutManagerOffer.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager gridLayoutManagerCategory = new GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false);

        LinearLayoutManager linearLayoutManagerCategory = new LinearLayoutManager(context);
        linearLayoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager linearLayoutManagerTouristPlaces = new LinearLayoutManager(context);
        linearLayoutManagerTouristPlaces.setOrientation(LinearLayoutManager.HORIZONTAL);

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
        onCreateNotice();

        //TOURIST PLACES
        recyclerViewTouristPlaces.setLayoutManager(linearLayoutManagerTouristPlaces);
        recyclerViewTouristPlaces.setHasFixedSize(false);
        adapterTouristPlaces = new TouristPlacesAdapter(context, arrayTouristPlaces);
        recyclerViewTouristPlaces.setAdapter(adapterTouristPlaces);


        offerDatabaseReference.addValueEventListener(offerValueEventListener);
        categoryDatabaseReference.addChildEventListener(categoryChildEventListener);
        noticeDatabaseReference.addValueEventListener(noticeValueEventListener);
        touristPlacesDatabaseReference.addValueEventListener(touristPlacesValueEventListener);

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
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void onCreateNotice() {
        arrayNotices = new ArrayList<>();

        noticeDatabaseReference = Utilities.getDatabaseReference()
                .child("7a4fa054-4287-4e9e-8432-258840d49798")
                .child("notices")
                .child("data");

        noticeValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notice notice = snapshot.getValue(Notice.class);
                    arrayNotices.add(notice);
                }

                adapterNotice = new NoticeAdapter(context, arrayNotices);
                viewPagerNotice.setAdapter(adapterNotice);
                viewPagerNotice.setClipToPadding(false);
                viewPagerNotice.setPadding(16, 0, 16, 0);
                noticeIndicator.setViewPager(viewPagerNotice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void onCreateDataUser(){
        String[] firstUserName =  Utilities.getCurrentUser("displayName").split(" ", 2);
        userName.setText(String.format(getResources().getString(R.string.say_hello_home_fragment), firstUserName[0]));

        Utilities.setImageFromUrl(context, Utilities.TYPE_CIRCLE, userImage,
                null, Utilities.getCurrentUser("photoUrl"));
    }
    private void onCreateTouristPlaces() {
        arrayTouristPlaces = new ArrayList<>();

        touristPlacesDatabaseReference = Utilities.getDatabaseReference()
                .child(Utilities.DB_NODE)
                .child("tourist_places");

        touristPlacesValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayTouristPlaces.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TouristPlace touristPlaces = snapshot.getValue(TouristPlace.class);
                    arrayTouristPlaces.add(touristPlaces);
                }
                adapterTouristPlaces.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }
}
