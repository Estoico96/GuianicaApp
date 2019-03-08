package com.ninjabyte.guianica.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.DetailOfferAdapter;
import com.ninjabyte.guianica.model.DetailOffer;
import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.ArrayList;

public class DetailOfferActivity extends AppCompatActivity {
    private final int INIT_POSITION = 0;

    private TextView textNotFound;


    private ValueEventListener detailValueEventListener;
    private DatabaseReference detailOfferDatabaseReference;

    private ArrayList<DetailOffer> arrayDetailOffer;

    private ViewPager viewPagerDetailOffer;
    private BubblePageIndicator indicatorDetailOffer;
    private DetailOfferAdapter detailOfferAdapter;

    private Bundle data;


    private TextView detailOfferTitle;
    private TextView detailOfferDescription;
    private TextView detailOfferExpire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_offer);

        detailOfferDatabaseReference = Utilities.getDatabaseReference();
        arrayDetailOffer = new ArrayList<>();
        data = getIntent().getExtras();

        textNotFound = findViewById(R.id.text_not_found_detail_offer);
        detailOfferTitle = findViewById(R.id.title_detail_offer);
        detailOfferDescription = findViewById(R.id.description_detail_offer);
        detailOfferExpire = findViewById(R.id.expire_detail_offer);


        viewPagerDetailOffer = findViewById(R.id.viewpager_banner_detail_offer);
        indicatorDetailOffer = findViewById(R.id.page_indicator_detail_offer);


        viewPagerDetailOffer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(final int position) {
                Toast.makeText(DetailOfferActivity.this, "position " + position, Toast.LENGTH_LONG).show();
                onCreateFadeIntTextAnimation(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (data != null) {
            onCreateDetailOffer(data.getString("uidOft"));
        }
    }


    private void onCreateDetailOffer(String uid) {
        detailOfferDatabaseReference = Utilities.getDatabaseReference()
                .child("7a4fa054-4287-4e9e-8432-258840d49798")
                .child("offers")
                .child("meta-data")
                .child(uid);

        detailValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayDetailOffer.clear();

                if (dataSnapshot.getChildrenCount() != 0){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DetailOffer detailOffer = snapshot.getValue(DetailOffer.class);
                        arrayDetailOffer.add(detailOffer);
                    }

                    detailOfferAdapter = new DetailOfferAdapter(DetailOfferActivity.this, arrayDetailOffer);
                    viewPagerDetailOffer.setAdapter(detailOfferAdapter);
                    viewPagerDetailOffer.setClipToPadding(false);
                    indicatorDetailOffer.setViewPager(viewPagerDetailOffer);

                    onCreateFadeIntTextAnimation(INIT_POSITION);

                    return;
                }

                textNotFound.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        detailOfferDatabaseReference.addValueEventListener(detailValueEventListener);

        Toast.makeText(DetailOfferActivity.this, "position " + viewPagerDetailOffer.getCurrentItem(), Toast.LENGTH_LONG).show();
    }


    private void onCreateFadeIntTextAnimation(int position){
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        anim.setFillAfter(true);


        detailOfferTitle.setText(arrayDetailOffer.get(position).getTitle());
        detailOfferDescription.setText(arrayDetailOffer.get(position).getDescription());
        detailOfferExpire.setText(arrayDetailOffer.get(position).getExpire());

        detailOfferTitle.startAnimation(anim);
        detailOfferDescription.startAnimation(anim);
        detailOfferExpire.startAnimation(anim);
    }
}
