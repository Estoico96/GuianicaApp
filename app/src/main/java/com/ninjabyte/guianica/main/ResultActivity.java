package com.ninjabyte.guianica.main;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.ResultAdapter;
import com.ninjabyte.guianica.model.Result;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView categoryName;
    private RecyclerView recyclerResult;
    private ResultAdapter resultAdapter;
    private ArrayList<Result> arrayResults;
    private ArrayList<String> arrayFilter;
    private TextView textDescriptionResults;
    private DatabaseReference companyDatabaseReference;
    private ValueEventListener resultValueEventListener;
    private Bundle bundle;
    private FloatingActionButton btnFilter;
    private RelativeLayout revealFilterLayout;
    private RadioGroup radioGroupFilter;
    private int position = 0;
    private boolean isHidden = false;
    private boolean isChange = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Utilities.checkApiLevel(ResultActivity.this);

        bundle = getIntent().getExtras();
        arrayResults = new ArrayList<>();
        arrayFilter = new ArrayList<>();

        categoryName = findViewById(R.id.category_name_result_activity);
        recyclerResult = findViewById(R.id.recycler_result_result_activity);


        recyclerResult.setHasFixedSize(false);
        recyclerResult.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapter(this, arrayResults);
        recyclerResult.setAdapter(resultAdapter);

        textDescriptionResults = findViewById(R.id.text_results_result_activity);

        revealFilterLayout = findViewById(R.id.filter_container_activity_result);
        btnFilter = findViewById(R.id.btn_filter_activity_result);


        resultValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayResults.clear();
                arrayFilter.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Result result = snapshot.getValue(Result.class);

                    arrayResults.add(result);
                    onCreateFilter(result);
                }


                resultAdapter.notifyDataSetChanged();
                textDescriptionResults.setText(String.format(getResources().getString(R.string.text_description_results), dataSnapshot.getChildrenCount()));

                initializeRadioButton(revealFilterLayout, arrayFilter);

                position = 0;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        btnFilter.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (bundle != null) categoryName.setText(bundle.getString("category_name"));

        companyDatabaseReference = Utilities.getDatabaseReference()
                .child(Utilities.DB_NODE)
                .child("companies")
                .child("data");

        companyDatabaseReference.addValueEventListener(resultValueEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (companyDatabaseReference != null)
            companyDatabaseReference.removeEventListener(resultValueEventListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter_activity_result:
                if (isChange){

                }
                startRevealEffect();

                break;
        }
    }

    private void startRevealEffect() {

        int x = revealFilterLayout.getRight();
        int y = revealFilterLayout.getTop();


        int startRadius;
        int endRadius;


        if (!isHidden) {

            startRadius = 0;
            endRadius = (int) Math.hypot(revealFilterLayout.getWidth(), revealFilterLayout.getHeight());


            btnFilter.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
            btnFilter.setImageResource(R.drawable.ic_close);


            Animator animationReveal = ViewAnimationUtils.createCircularReveal(revealFilterLayout, x, y, startRadius, endRadius);
            animationReveal.setInterpolator(new AccelerateDecelerateInterpolator());
            animationReveal.setDuration(500);


            revealFilterLayout.setVisibility(View.VISIBLE);

            animationReveal.start();
            isHidden = true;


        } else {

            startRadius = (int) Math.hypot(revealFilterLayout.getWidth(), revealFilterLayout.getHeight());
            endRadius = 0;

            btnFilter.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null)));
            btnFilter.setImageResource(R.drawable.ic_filter_list_white);

            Animator animationHide = ViewAnimationUtils.createCircularReveal(revealFilterLayout, x, y, startRadius, endRadius);
            animationHide.setInterpolator(new AccelerateDecelerateInterpolator());
            animationHide.setDuration(500);

            animationHide.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    revealFilterLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

            animationHide.start();

            isHidden = false;
        }
    }

    public void initializeRadioButton(RelativeLayout container, ArrayList<String> arrayFilter) {

        if (radioGroupFilter != null) revealFilterLayout.removeView(radioGroupFilter);

        radioGroupFilter  = new RadioGroup(this);
        radioGroupFilter.setOrientation(RadioGroup.VERTICAL);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Set the RadioGroup position below the Button
        layoutParams.addRule(RelativeLayout.BELOW, R.id.title_filter_activity_result);

        // Apply the layout parameters for RadioGroup
        radioGroupFilter.setLayoutParams(layoutParams);


        RadioButton defaultRb = new RadioButton(this);
        defaultRb.setText("Todos");
        defaultRb.setTextColor(Color.BLACK);
        defaultRb.setId(0);
        defaultRb.setTypeface(ResourcesCompat.getFont(this, R.font.nunito_sans_bold));
        defaultRb.setTextSize(18);
        radioGroupFilter.addView(defaultRb);

        // Create a Radio Button for RadioGroup

        for (int i = 0; i <= arrayFilter.size() - 1; i++) {
            RadioButton filter = new RadioButton(this);
            filter.setText(arrayFilter.get(i));
            filter.setId(i + 1);
            filter.setTextColor(Color.BLACK);
            filter.setTypeface(ResourcesCompat.getFont(this, R.font.nunito_sans_bold));
            filter.setTextSize(18);
            radioGroupFilter.addView(filter);
        }

        // Finally, add the RadioGroup to main layout
        container.addView(radioGroupFilter);

        radioGroupFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnFilter.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreen)));
                btnFilter.setImageResource(R.drawable.ic_done);

                isChange = true;

            }
        });
    }

    private void onCreateFilter(Result result) {
        String nameFilter = result.getSpecialty().substring(15, result.getSpecialty().length());

            if (arrayFilter.size() != 0){
                if (!nameFilter.equals(arrayFilter.get(position))) {
                    arrayFilter.add(nameFilter);
                    position++;
                }
            }else {
                arrayFilter.add(nameFilter);
            }

    }
}
