package com.ninjabyte.guianica;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.adapters.ContactRecyclerAdapter;
import com.ninjabyte.guianica.model.Telephone;

import java.util.ArrayList;

public class CreateButtonSheetHelper {

    public static final String TYPE_TELEPHONE = "telephones";
    public static final String TYPE_LOCATION = "locations";
    public static final String TYPE_EMAIL = "emails";

    private Activity activity;
    private Context context;

    private ArrayList<Telephone> arrayTelephones;
    private ArrayList<String> arrayLocations;
    private ArrayList<String> arrayEmails;

    private RecyclerView recyclerContact;
    private ContactRecyclerAdapter contactAdapter;
    private ProgressBar progressBar;

    private DatabaseReference contactDatabaseReference;
    private ValueEventListener valueEventListener;

    public CreateButtonSheetHelper(Activity activity, String uid, String option, Context context) {

        this.activity = activity;
        this.context = context;

        contactDatabaseReference = Utilities.getDatabaseReference()
                .child(Utilities.DB_NODE)
                .child("companies")
                .child("meta-data")
                .child("4e9e-8432-258840d49798")
                .child("contacts")
                .child(uid);

        View view = activity.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_profile_ativity, null);
        recyclerContact = view.findViewById(R.id.recycler_contact_profile_activity);

        progressBar = view.findViewById(R.id.progress_profile_activity);

        TextView sheetTitle = view.findViewById(R.id.title_contact_profile_activity);
        sheetTitle.setText(option);
        recyclerContact.setLayoutManager(new LinearLayoutManager(context));
        recyclerContact.setHasFixedSize(false);
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerContact.addItemDecoration(decoration);

        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.show();
    }


    public void getArrayTelephones() {

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayTelephones = new ArrayList<>();
                arrayTelephones.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Telephone telephone = snapshot.getValue(Telephone.class);
                    arrayTelephones.add(telephone);
                }

                contactAdapter = new ContactRecyclerAdapter(activity.getApplicationContext(), arrayTelephones, null, 1);
                recyclerContact.setAdapter(contactAdapter);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
       contactDatabaseReference.addValueEventListener(valueEventListener);

       new android.os.Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               contactDatabaseReference.removeEventListener(valueEventListener);
           }
       },2000);
    }



    public void getArrayLocation() {

        valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayLocations = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String locations = snapshot.getValue(String.class);
                    arrayLocations.add(locations);
                }

                contactAdapter = new ContactRecyclerAdapter(activity.getApplicationContext(), null, arrayLocations, 2);
                recyclerContact.setAdapter(contactAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        contactDatabaseReference.addValueEventListener(valueEventListener);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                contactDatabaseReference.removeEventListener(valueEventListener);
            }
        },2000);

    }

    public ArrayList<Telephone> getArrayEmail() {

        contactDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayTelephones = new ArrayList<>();
                arrayTelephones.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Telephone telephone = snapshot.getValue(Telephone.class);
                    arrayTelephones.add(telephone);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        contactDatabaseReference.removeEventListener(valueEventListener);

        return arrayTelephones;
    }


}
