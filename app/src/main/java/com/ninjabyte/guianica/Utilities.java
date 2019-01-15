package com.ninjabyte.guianica;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ninjabyte.guianica.model.About;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Utilities {
    private static DatabaseReference databaseReference;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_NORMAL = 1;

    private Utilities() {
    }

    public static void showSnackBar(View contextView, String message) {
        Snackbar.make(contextView, message, Snackbar.LENGTH_LONG)
                .show();

    }

    public static ArrayList<About> getAboutContent(Context context) {
        ArrayList<About> abouts = new ArrayList<>();

        abouts.add(new About(context.getResources().getString(R.string.text_about_slide_title_one),
                context.getResources().getString(R.string.text_about_slide_description_one)));

        abouts.add(new About(context.getResources().getString(R.string.text_about_slide_title_two),
                context.getResources().getString(R.string.text_about_slide_description_two)));

        abouts.add(new About(context.getResources().getString(R.string.text_about_slide_title_three),
                context.getResources().getString(R.string.text_about_slide_description_three)));

        return abouts;
    }

    public static void setFragment(Fragment fragment, Context context, int container) {

        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment, null);
        fragmentTransaction.commit();

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

    public static String getCurrentUser(String option) {
        String response = "null";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            switch (option) {
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
}
