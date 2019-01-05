package com.ninjabyte.guianica.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.AboutAdapter;
import com.ninjabyte.guianica.main.MainActivity;
import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import de.hdodenhof.circleimageview.CircleImageView;


public class ConfirmFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    private Context context;
    private View view;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    public ConfirmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        view = inflater.inflate(R.layout.fragment_confirm, container, false);

        CircleImageView userImage = view.findViewById(R.id.user_image_fragment_confirm);
        TextView userName = view.findViewById(R.id.user_name_fragment_confirm);
        Button btnConfirm = view.findViewById(R.id.btn_next_fragment_confirm);

        Utilities.setImageFromUrl(context, Utilities.TYPE_CIRCLE, userImage, null, Utilities.getCurrentUser("photoUrl"));
        userName.setText(currentUser.getDisplayName());

        btnConfirm.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next_fragment_confirm){
            startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
        }
    }

}
