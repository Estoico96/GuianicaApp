package com.ninjabyte.guianica.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.main.MainActivity;
import com.ninjabyte.guianica.model.User;

import de.hdodenhof.circleimageview.CircleImageView;


public class ConfirmFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AuthActivity";
    private Context context;
    private View view;
    private ShimmerFrameLayout shimmerFramengConfirm;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference userDatabaseReference;
    private CircleImageView userImage;
    private TextView userName;
    private TextView description;
    private Button btnConfirm;

    public ConfirmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        view = inflater.inflate(R.layout.fragment_confirm, container, false);

        shimmerFramengConfirm = view.findViewById(R.id.shimmer_view_container_fragment_confirm);
        userImage = view.findViewById(R.id.user_image_fragment_confirm);
        userName = view.findViewById(R.id.user_name_fragment_confirm);
        description = view.findViewById(R.id.description_fragment_confirm);
        btnConfirm = view.findViewById(R.id.btn_next_fragment_confirm);

        btnConfirm.setOnClickListener(this);
        shimmerFramengConfirm.startShimmer();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        userDatabaseReference = Utilities.getDatabaseReference()
                .child(Utilities.DB_USER_NODE)
                .child("Users").child(currentUser.getUid());

            registerUser();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next_fragment_confirm) {
           Utilities.saveUserPreferences(
                   context, "boolean",
                   "isAuth",
                   view.findViewById(R.id.fragment_main_activity_splash),
                   0, true, null);

           startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
        }
    }

    private void registerUser() {
        if (currentUser != null) {
            User user = new User(
                    currentUser.getUid(),
                    currentUser.getDisplayName(),
                    currentUser.getPhotoUrl().toString(),
                    currentUser.getEmail()
            );
            userDatabaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                        downloadDataUser();
                    }
                }
            });

        }
    }

    private void downloadDataUser() {
        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadDataUser(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Utilities.showSnackBar(view, databaseError.getMessage());
            }
        });
    }

    private void loadDataUser(User user) {
        shimmerFramengConfirm.stopShimmer();
        shimmerFramengConfirm.setVisibility(View.GONE);
        userImage.setVisibility(View.VISIBLE);
        userName.setVisibility(View.VISIBLE);

        Utilities.setImageFromUrl(context, Utilities.TYPE_CIRCLE, userImage, null, user.photoUrl);
        userName.setText(user.name);
        description.setText(getText(R.string.text_description_fragment_confirm));

        btnConfirm.setVisibility(View.VISIBLE);
    }
}
