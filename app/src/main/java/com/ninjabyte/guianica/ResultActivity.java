package com.ninjabyte.guianica;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.adapters.ResultAdapter;
import com.ninjabyte.guianica.model.Result;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private TextView categoryName;
    private RecyclerView recyclerResult;
    private ResultAdapter resultAdapter;
    private ArrayList<Result> arrayResults;
    private TextView textDescriptionResults;
    private DatabaseReference companyDatabaseReference;
    private ValueEventListener resultValueEventListener;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Utilities.checkApiLevel(ResultActivity.this);

        bundle = getIntent().getExtras();
        arrayResults = new ArrayList<>();

        categoryName = findViewById(R.id.category_name_result_activity);
        recyclerResult = findViewById(R.id.recycler_result_result_activity);
        recyclerResult.setHasFixedSize(false);
        recyclerResult.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapter(this, arrayResults);
        recyclerResult.setAdapter(resultAdapter);

        textDescriptionResults = findViewById(R.id.text_results_result_activity);



        resultValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayResults.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Result result = snapshot.getValue(Result.class);
                    arrayResults.add(result);
                }
                resultAdapter.notifyDataSetChanged();
                textDescriptionResults.setText(String.format(getResources().getString(R.string.text_description_results), dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
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
        if (companyDatabaseReference != null) companyDatabaseReference.removeEventListener(resultValueEventListener);
    }
}
