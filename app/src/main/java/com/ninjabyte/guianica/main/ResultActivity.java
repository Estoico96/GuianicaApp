package com.ninjabyte.guianica.main;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.FilterAdapter;
import com.ninjabyte.guianica.adapters.ResultAdapter;
import com.ninjabyte.guianica.model.Result;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    private final String DEFAULT_FILTERED_LIST = "Todos";
    private ShimmerFrameLayout shimmerActivityResult;
    private RelativeLayout relativeContaierResult;
    private TextView categoryName;
    private TextView btnMoreOffer;
    private ResultAdapter resultAdapter;
    private FilterAdapter filterAdapter;
    private ArrayList<Result> arrayResults;
    private ArrayList<String> arrayFilter;
    private RecyclerView recyclerResult;
    private TextView textDescriptionResults;
    private Query companyQueryDatabaseReference;
    private ValueEventListener resultValueEventListener;
    private Bundle bundle;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Utilities.checkApiLevel(ResultActivity.this);

        bundle = getIntent().getExtras();
        arrayResults = new ArrayList<>();
        arrayFilter = new ArrayList<>();

        shimmerActivityResult = findViewById(R.id.shimmer_view_container);
        shimmerActivityResult.startShimmer();
        relativeContaierResult = findViewById(R.id.relative_container_activity_result);
        categoryName = findViewById(R.id.category_name_result_activity);
        recyclerResult = findViewById(R.id.recycler_result_result_activity);
        btnMoreOffer = findViewById(R.id.btn_more_offer_profile_activity);
        RecyclerView recyclerFilter = findViewById(R.id.recycler_filter_result_activity);

        recyclerResult.setHasFixedSize(false);
        recyclerResult.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapter(this, arrayResults, recyclerResult);
        recyclerResult.setAdapter(resultAdapter);


        recyclerFilter.setHasFixedSize(false);
        LinearLayoutManager filterLinearLayoutManager = new LinearLayoutManager(this);
        filterLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerFilter.setLayoutManager(filterLinearLayoutManager);
        filterAdapter = new FilterAdapter(this, arrayFilter, resultAdapter);
        recyclerFilter.setAdapter(filterAdapter);

        textDescriptionResults = findViewById(R.id.text_results_result_activity);

        resultValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayResults.clear();
                arrayFilter.clear();
                arrayFilter.add(DEFAULT_FILTERED_LIST);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Result result = snapshot.getValue(Result.class);
                    arrayResults.add(result);
                    onCreateFilter(result);
                }

                Utilities.runLayoutAnimation(recyclerResult, ResultActivity.this);

                filterAdapter.notifyDataSetChanged();
                textDescriptionResults.setText(
                        String.format(getResources().getString(R.string.text_description_results),
                                dataSnapshot.getChildrenCount()));

                shimmerActivityResult.stopShimmer();
                shimmerActivityResult.setVisibility(View.GONE);
                relativeContaierResult.setVisibility(View.VISIBLE);
                position = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bundle != null){
            categoryName.setText(bundle.getString("category_name"));
            companyQueryDatabaseReference = Utilities.getDatabaseReference()
                    .child(Utilities.DB_NODE)
                    .child("companies")
                    .child("data").orderByChild("hashtag").equalTo(bundle.getString("hashtag_name"));
            companyQueryDatabaseReference.addValueEventListener(resultValueEventListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (companyQueryDatabaseReference != null)
            companyQueryDatabaseReference.removeEventListener(resultValueEventListener);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_more_offer_profile_activity){

        }
    }

    private void onCreateFilter(Result result) {
        String nameFilter = result.getSpecialty();
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
