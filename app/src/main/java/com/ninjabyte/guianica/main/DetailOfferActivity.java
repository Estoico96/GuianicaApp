package com.ninjabyte.guianica.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.DetailOfferAdapter;
import com.ninjabyte.guianica.custom.HeightWrappingViewPager;
import com.ninjabyte.guianica.model.DetailOffer;
import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailOfferActivity extends AppCompatActivity implements View.OnClickListener {
    private final int INIT_POSITION = 0;
    private final int LAYOUT_MAIN = R.id.layout_activity_detail_offer;

    private TextView textNotFound;


    private ValueEventListener detailValueEventListener;
    private DatabaseReference detailOfferDatabaseReference;

    private ArrayList<DetailOffer> arrayDetailOffer;

    private HeightWrappingViewPager viewPagerDetailOffer;
    private BubblePageIndicator indicatorDetailOffer;
    private DetailOfferAdapter detailOfferAdapter;
    private NestedScrollView scrollView;

    private Bundle data;

    private CircleImageView companyLogo;
    private TextView companyName;

    private Button shareOffer;

    private TextView detailOfferTitle;
    private TextView detailOfferDescription;
    private TextView detailOfferExpire;

    private Animation animationZoomOut;
    private Animation animationZoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_offer);
        Utilities.checkApiLevel(DetailOfferActivity.this);

        animationZoomOut = AnimationUtils.loadAnimation(DetailOfferActivity.this, R.anim.animation_zoom_out);
        animationZoomIn = AnimationUtils.loadAnimation(DetailOfferActivity.this, R.anim.animation_zoom_in);

        detailOfferDatabaseReference = Utilities.getDatabaseReference();
        arrayDetailOffer = new ArrayList<>();
        data = getIntent().getExtras();

        scrollView = findViewById(R.id.scroll_view_activity_detail_offer);

        companyLogo = findViewById(R.id.logo_company_detail_offer);
        companyName = findViewById(R.id.company_name_detail_offer);

        textNotFound = findViewById(R.id.text_not_found_detail_offer);
        shareOffer = findViewById(R.id.btn_share_detail_offer);
        detailOfferTitle = findViewById(R.id.title_detail_offer);
        detailOfferDescription = findViewById(R.id.description_detail_offer);
        detailOfferExpire = findViewById(R.id.expire_detail_offer);


        viewPagerDetailOffer = findViewById(R.id.viewpager_banner_detail_offer);
        indicatorDetailOffer = findViewById(R.id.page_indicator_detail_offer);


        viewPagerDetailOffer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {/**/}

            @Override
            public void onPageSelected(final int position) {
                Toast.makeText(DetailOfferActivity.this, "position " + position, Toast.LENGTH_LONG).show();
                onCreateFadeIntTextAnimation(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {/**/}
        });


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY){
                    if (shareOffer.getVisibility() == View.VISIBLE){
                        shareOffer.startAnimation(animationZoomOut);
                        shareOffer.setVisibility(View.INVISIBLE);
                    }
                }
                if (scrollY < oldScrollY){
                    if (shareOffer.getVisibility() == View.INVISIBLE){
                        shareOffer.startAnimation(animationZoomIn);
                        shareOffer.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        shareOffer.setOnClickListener(this);

        //VISITAR PERFIL DESDE LAS OFERTAS
        //companyName.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (data != null) {
            Utilities.setImageFromUrl(
                    DetailOfferActivity.this, Utilities.TYPE_CIRCLE,
                    companyLogo, null, data.getString("logoCmpny"));

            companyName.setText(data.getString("nameCmpny"));
            onCreateDetailOffer(data.getString("uidOft"));
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View v = findViewById(R.id.viewpager_banner_detail_offer);
        String x = Integer.toString(v.getWidth());
        String y = Integer.toString(v.getHeight());
        //show ImageView width and height
        Log.v("viewpager-height:" ,  x + " " + y);
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

                if (dataSnapshot.getChildrenCount() != 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DetailOffer detailOffer = snapshot.getValue(DetailOffer.class);
                        arrayDetailOffer.add(detailOffer);
                    }

                    detailOfferAdapter = new DetailOfferAdapter(DetailOfferActivity.this, DetailOfferActivity.this, arrayDetailOffer);
                    viewPagerDetailOffer.setAdapter(detailOfferAdapter);
                    viewPagerDetailOffer.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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


    private void onCreateFadeIntTextAnimation(int position) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share_detail_offer:
               final ProgressBar progressBar = findViewById(R.id.progress_share_offer_activity_detail_offer);
                progressBar.setVisibility(View.VISIBLE);

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Utilities.shareContent(DetailOfferActivity.this,
                                detailOfferAdapter.getImageOffer(),
                                progressBar,
                                findViewById(LAYOUT_MAIN));
                    }
                });

                break;

            case R.id.company_name_detail_offer:
                Intent intent = new Intent(DetailOfferActivity.this, ProfileActivity.class);
                intent.putExtra("uidCmpny", data.getString("uidCmpny"));
                intent.putExtra("oftKey", "oft");
                startActivity(new Intent(DetailOfferActivity.this, ProfileActivity.class));
                break;
        }
    }
}
