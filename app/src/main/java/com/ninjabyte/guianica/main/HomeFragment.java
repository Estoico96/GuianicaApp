package com.ninjabyte.guianica.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.OfferHomefragmentAdapter;
import com.ninjabyte.guianica.model.Company;
import com.ninjabyte.guianica.model.MetaOffer;
import com.ninjabyte.guianica.model.Offer;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private Context context;
    private Activity activity;
    private CircleImageView userImage;
    private RecyclerView recyclerViewOffer;
    private OfferHomefragmentAdapter adapterOffer;
    private ArrayList<Offer> arrayOffers;
    private DatabaseReference databaseReference;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();

        arrayOffers = new ArrayList<>();

        databaseReference = Utilities.getDatabaseReference()
                .child("7a4fa054-4287-4e9e-8432-258840d49798")
                .child("offers")
                .child("data");

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Utilities.checkApiLevel(activity);

        View fragmentHomeView = inflater.inflate(R.layout.fragment_home, container, false);

        userImage = fragmentHomeView.findViewById(R.id.user_image_fragment_home);
        Utilities.setImageFromUrl(context,Utilities.TYPE_CIRCLE,  userImage, null,Utilities.getCurrentUser("photoUrl"));

        recyclerViewOffer = fragmentHomeView.findViewById(R.id.recycler_offers_home_fragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewOffer.setLayoutManager(linearLayoutManager);
        adapterOffer =  new OfferHomefragmentAdapter(context, arrayOffers);
        recyclerViewOffer.setAdapter(adapterOffer);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayOffers.clear();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Offer offer = snapshot.getValue(Offer.class);
                    arrayOffers.add(offer);

                    Log.v("asdf", offer.toString());
                }


                Log.v("asdf", "notify");
                adapterOffer.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return fragmentHomeView;
    }
}
