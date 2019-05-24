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
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.adapters.ContactRecyclerAdapter;
import com.ninjabyte.guianica.model.Telephone;

import java.util.ArrayList;

public class ContactSheetHelper {

    public static final String TYPE_TELEPHONE = "telephones";
    public static final String TYPE_LOCATION = "locations";
    public static final String TYPE_EMAIL = "emails";

    private Activity activity;

    private DataSnapshot snapshot;

    private ArrayList<Telephone> arrayTelephones;
    private ArrayList<String> arrayLocations;
    private ArrayList<String> arrayEmails;

    private ContactRecyclerAdapter contactAdapter;

    public ContactSheetHelper(Activity activity,  Context context, String type, DataSnapshot snapshot) {

        this.activity = activity;
        this.snapshot = snapshot;

        arrayTelephones = new ArrayList<>();
        arrayLocations = new ArrayList<>();

        View view = activity.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_profile_ativity, null);
        RecyclerView recyclerContact = view.findViewById(R.id.recycler_contact_profile_activity);

        ProgressBar progressBar = view.findViewById(R.id.progress_profile_activity);

        TextView sheetTitle = view.findViewById(R.id.title_contact_profile_activity);
        sheetTitle.setText(type);

        recyclerContact.setLayoutManager(new LinearLayoutManager(context));
        recyclerContact.setHasFixedSize(false);
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerContact.addItemDecoration(decoration);

        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.show();

        switch (type){
            case TYPE_TELEPHONE:
                initializeViewTelephones();
                break;

            case TYPE_LOCATION:
                initializeViewLocations();
                break;
        }

        recyclerContact.setAdapter(contactAdapter);
        progressBar.setVisibility(View.GONE);
    }


    private void initializeViewTelephones() {

        arrayTelephones.clear();

        for (DataSnapshot snap : snapshot.child(ContactSheetHelper.TYPE_TELEPHONE).getChildren()){
            Telephone telephone = snap.getValue(Telephone.class);
            arrayTelephones.add(telephone);
        }

        contactAdapter = new ContactRecyclerAdapter(activity.getApplicationContext(), arrayTelephones, null, 1);
    }


    private void initializeViewLocations() {
        arrayLocations.clear();

        for (DataSnapshot snap : snapshot.child(ContactSheetHelper.TYPE_LOCATION).getChildren()){
            String locations = snap.getValue(String.class);
            arrayLocations.add(locations);
        }

        contactAdapter = new ContactRecyclerAdapter(activity.getApplicationContext(), null, arrayLocations, 2);
    }

}
