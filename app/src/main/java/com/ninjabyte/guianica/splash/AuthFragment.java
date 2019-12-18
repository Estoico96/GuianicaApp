package com.ninjabyte.guianica.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.adapters.AboutAdapter;
import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;


public class AuthFragment extends Fragment implements View.OnClickListener {
    private static final int FRAGMENT_CONTAINER = R.id.fragment_main_activity_splash;
    private static final int RC_SIGN_IN = 1;
    private TextView privacyPolicy;
    private static final String TAG = "AuthActivity";

    private ViewPager aboutViewPager;
    private BubblePageIndicator indicator;
    private RelativeLayout layoutAuth;
    private ProgressBar progressAuth;
    private FirebaseAuth mAuth;
    private Button signInButton;
    private GoogleSignInClient googleSignInClient;
    private Context context;
    private Activity activity;
    private View view;

    public AuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auth, container, false);

        aboutViewPager = view.findViewById(R.id.about_view_pager_fragment_auth);
        indicator = view.findViewById(R.id.indicator_fragment_about);
        progressAuth = view.findViewById(R.id.progress_fragment_auth);
        layoutAuth = view.findViewById(R.id.layout_auth_fragment);
        CheckBox acceptPrivacyPolicyBox = view.findViewById(R.id.accept_privacy_policy_fragment_auth);
        signInButton = view.findViewById(R.id.btn_sign_in_fragment_auth);
        privacyPolicy = view.findViewById(R.id.privacy_policy_fragment_auth);

        privacyPolicy.setText(Html.fromHtml(getString(R.string.text_privacy_policy_auth)));
        BetterLinkMovementMethod.linkifyHtml(privacyPolicy).setOnLinkClickListener(new BetterLinkMovementMethod.OnLinkClickListener() {
            @Override
            public boolean onClick(TextView textView, String url) {
                showPrivacyPolicyDialog();
                return true;
            }
        });

        aboutViewPager.setAdapter(new AboutAdapter(getContext()));
        indicator.setViewPager(aboutViewPager/*, (int) Math.floor(Utilities.getAboutContent().size() / 2)*/);

        acceptPrivacyPolicyBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utilities.saveDecisionPrivacyPolicy(context, isChecked);
            }
        });
        signInButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();
        mAuth = FirebaseAuth.getInstance();
        Utilities.saveDecisionPrivacyPolicy(context, false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(context, gso);

    }

    public void showPrivacyPolicyDialog() {
        DialogFragment dialog = new PrivacyPolicyDialogFragment();
        dialog.show(((FragmentActivity) context).getSupportFragmentManager(), "DialogFragment");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in_fragment_auth:
                signIn();
                break;
            case R.id.privacy_policy_fragment_auth:
                startActivity(new Intent(context, PrivacyPolicyWebViewActivity.class));
                break;
        }

    }

    private void signIn() {
        if (Utilities.getDecisionPrivacyPolicy(context)){
            animAuth();
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }else {
            Utilities.showSnackBar(layoutAuth, getString(R.string.text_app_error_privacy_policy));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                    return;
                }
                failureAnimAuth();
               Utilities.showSnackBar(layoutAuth, getString(R.string.text_app_error));
            } catch (ApiException e) {
                failureAnimAuth();
                Utilities.showSnackBar(layoutAuth, getString(R.string.text_app_error_sing_in_google));
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Utilities.setFragment(new ConfirmFragment(), getContext(), FRAGMENT_CONTAINER);
                        } else {
                           failureAnimAuth();
                            Utilities.showSnackBar(layoutAuth, getString(R.string.text_app_error_auth));
                        }
                    }
                });
    }



    private void animAuth(){
        progressAuth.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.INVISIBLE);
    }

    private void failureAnimAuth(){
        signInButton.setVisibility(View.VISIBLE);
        progressAuth.setVisibility(View.INVISIBLE);
    }
}
