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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.FilterAdapter;
import com.ninjabyte.guianica.adapters.ResultAdapter;
import com.ninjabyte.guianica.model.Result;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView categoryName;
    private ResultAdapter resultAdapter;
    private FilterAdapter filterAdapter;
    private ArrayList<Result> arrayResults;
    private ArrayList<String> arrayFilter;
    private TextView textDescriptionResults;
    private DatabaseReference companyDatabaseReference;
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

        categoryName = findViewById(R.id.category_name_result_activity);
        RecyclerView recyclerResult = findViewById(R.id.recycler_result_result_activity);
        RecyclerView recyclerFilter = findViewById(R.id.recycler_filter_result_activity);

        recyclerResult.setHasFixedSize(false);
        recyclerResult.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapter(this, arrayResults);
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

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Result result = snapshot.getValue(Result.class);
                    arrayResults.add(result);
                    onCreateFilter(result);
                }

                resultAdapter.notifyDataSetChanged();
                filterAdapter.notifyDataSetChanged();
                textDescriptionResults.setText(
                        String.format(getResources().getString(R.string.text_description_results),
                                dataSnapshot.getChildrenCount()));
                position = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
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
