package com.ninjabyte.guianica;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ninjabyte.guianica.interfaces.buttonDialogListener;
import com.ninjabyte.guianica.model.About;
import com.ninjabyte.guianica.model.Connection;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Utilities{
    private static DatabaseReference databaseReference;
    private static SharedPreferences userPreferences;
    private static SharedPreferences userAuthPreferences;
    private static boolean stateConnection = true;

    public static final String USER_PREFERENCES = "usrpfrncs";
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_NORMAL = 1;
    public static final String DB_NODE = "7a4fa054-4287-4e9e-8432-258840d49798";
    public static final String DB_USER_NODE = "51b563d2-9c45-11e9-a2a3-2a2ae2dbcce4";
    public static final String PRIVACY_POLICY_URL = "https://guianica-db801.firebaseapp.com";

    private static SharedPreferences privacyPolicySharedPreferences;
    private static SharedPreferences commertialIntSharedPreferences;
    private static String proberbs [] = {
            "A todo mamón.,",
            "El que no llora no mama.",
            "El que quiere celeste que le cueste.",
            "El que madruga come pechuga.",
            "Echar un pelón.",
            "Clase guayola.",
            "A todo full.",
            "En barajustada.",
            "Encimita.",
            "Ay nomacito.",
            "Para allacito.",
            "A mecate corto.",
            "Como perro con pajuelilla.",
            "Como la novia de tola."
    };

    private Utilities() {
    }

    public static void showSnackBar(View contextView, String message) {
        Snackbar.make(contextView, message, Snackbar.LENGTH_LONG)
                .show();
    }

    public static ArrayList<About> getAboutContent(Context context) {
        ArrayList<About> abouts = new ArrayList<>();
        abouts.add(new About(context.getResources().getDrawable(R.drawable.ic_welcome), context.getResources().getString(R.string.text_about_slide_title_one),
                context.getResources().getString(R.string.text_about_slide_description_one)));
        abouts.add(new About(context.getResources().getDrawable(R.drawable.ic_search), context.getResources().getString(R.string.text_about_slide_title_two),
                context.getResources().getString(R.string.text_about_slide_description_two)));
        abouts.add(new About(context.getResources().getDrawable(R.drawable.ic_space), context.getResources().getString(R.string.text_about_slide_title_three),
                context.getResources().getString(R.string.text_about_slide_description_three)));
        return abouts;
    }

    public static void setFragment(Fragment fragment, Context context, int container) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment, null);
        fragmentTransaction.commit();
    }

    public static Connection checkConnetion(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        boolean isWiFi = activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        stateConnection = isConnected;

        return new Connection(isConnected, isWiFi);
    }

    public static void checkApiLevel(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = activity.getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            return;
        }
        Window window = activity.getWindow();
        window.setStatusBarColor(Color.parseColor("#D5D5D5"));
    }

    public static void setImageFromUrl(Context context, int type, CircleImageView circleImage, ImageView normalImage, String url) {
        switch (type) {
            case TYPE_CIRCLE:
                Glide.with(context)
                        .load(url)
                        .placeholder(R.drawable.place_holder)
                        .dontAnimate()
                        .into(circleImage);
                break;
            case TYPE_NORMAL:
                Glide.with(context)
                        .load(url)
                        .placeholder(R.drawable.place_holder)
                        .dontAnimate()
                        .into(normalImage);
                break;
        }
    }

    public static String getCurrentUser(String request) {
        String response = "null";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            switch (request) {
                case "photoUrl":
                    response = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "https://www.cornwallbusinessawards.co.uk/wp-content/uploads/2017/11/dummy450x450.jpg";
                    break;
                case "displayName":
                    response = user.getDisplayName();
                    break;
                case "userUid":
                    response = user.getUid();
                    break;
                case "userEmail":
                    response = user.getEmail();
                    break;
            }
        }
        return response;
    }

    public static DatabaseReference getDatabaseReference() {
        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static void setMargins(View view, int left, int top, int right, int bottom, String direction) {
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        switch (direction) {
            case "END":
                p.setMarginEnd(right);
                break;
            case "ALL":
                p.setMargins(left, top, right, bottom);
                break;
        }
        view.requestLayout();
    }

    public static void setPaddings(View view, int left, int top, int right, int bottom) {

    }

    public static void runLayoutAnimation(RecyclerView recyclerView, Context context) {
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setLayoutAnimation(controller);
        if (recyclerView != null) recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public static void saveUserPreferences(
            @NonNull Context context, @NonNull String var, String key, View snackView, int i, boolean b, String s) {

        userPreferences = context.getSharedPreferences(USER_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        switch (var) {
            case "boolean":
                editor.putBoolean(key, b);
                break;

            case "int" :
                editor.putInt(key, i);
                break;

            case "String" :
            editor.putString(key, s);
            break;

            default:
                Utilities.showSnackBar(snackView, "Ha ocurrido un error mientras se guardaban las preferencias del usuario.");
        }
        editor.apply();

    }

    public static boolean userFinishedAuth(Context context){
        if (userAuthPreferences == null){
            userPreferences = context.getSharedPreferences(USER_PREFERENCES, context.MODE_PRIVATE);
        }
        return userPreferences.getBoolean("isAuth", false);
    }

    public static void saveDecisionPrivacyPolicy(Context context, boolean state) {
        privacyPolicySharedPreferences = context
                .getSharedPreferences(context.getString(R.string.privacy_policy_key), context.MODE_PRIVATE);

        SharedPreferences.Editor editor = privacyPolicySharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.privacy_policy_state_key), state);
        editor.apply();

    }

    public static boolean getDecisionPrivacyPolicy(Context context) {
        return privacyPolicySharedPreferences.getBoolean(context.getString(R.string.privacy_policy_state_key), false);
    }

    public static void showDialog(Context context,  buttonDialogListener selectedButtonDialogListener, int title, int content, String textPButton, String textNButton){

       final buttonDialogListener dialogListener = selectedButtonDialogListener;

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setTitle(context.getResources().getString(title));
        builder.setMessage(context.getResources().getString(content));
        builder.setCancelable(false);

        builder.setPositiveButton(textPButton,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onClickPositiveButton(dialog);
                    }
                });

        builder.setNegativeButton(textNButton,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onClickNegativeButton(dialog);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String getRandomProverb(){
        Random random = new Random();
        int x = random.nextInt(proberbs.length);
        return proberbs[x];
    }

    public static boolean getStateConnection(){
     return stateConnection;
    }

    public static int getCommercialInt(Context context, int size){

        Random random = new Random();
        int x = random.nextInt(size);

        if (commertialIntSharedPreferences == null){
            saveCommercialInt(context, x);
        }

        int l = commertialIntSharedPreferences.getInt(context.getString(R.string.last_commercial_int_key), 0);
        if (x == l){
            if (x < size){
                ++x;
            }else {
                x = 0;
            }
        }

        saveCommercialInt(context, x);

        Log.v("randomLastInt ", "last_: "+l +" now_: "+x);
        return x;
    }

    private static void saveCommercialInt(Context context, int i){
        commertialIntSharedPreferences = context
                .getSharedPreferences(context.getString(R.string.commercial_int_key), context.MODE_PRIVATE);

        SharedPreferences.Editor editor = commertialIntSharedPreferences.edit();
        editor.putInt(context.getString(R.string.last_commercial_int_key), i);
        editor.apply();
    }
}
