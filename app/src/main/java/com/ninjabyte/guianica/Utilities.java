package com.ninjabyte.guianica;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ninjabyte.guianica.model.About;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Utilities {
    private Utilities() {
    }

    public static void showSnackBar(View contextView, String message) {
        Snackbar.make(contextView, message, Snackbar.LENGTH_LONG)
                .show();

    }

    public static ArrayList<About> getAboutContent(){
        ArrayList<About> abouts = new ArrayList<>();

        abouts.add(new About("¡Gracias por descargarme!",
                "Soy una guia digital dónde encontrarás información de contacto y ofertas de distintos negocios comerciales."));
        abouts.add(new About("Encuentra los negocios comerciales entre las distintas categorías.",
                "Destro del resultado de busqueda podrás filtrar los negocios por su especialidad para encontar el que más se adapte a tu necesidad."));

        abouts.add(new About("No te preocupes, funciono sin conexión a internet.", "En cada perfil de los distintos negocios podrás guardar su información de contácto si asi lo deseas para esos días en el espacio intergaláctico."));

        return abouts;
    }

    public static void setFragment(Fragment fragment, Context context, int container) {

        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment, null);
        fragmentTransaction.commit();

    }


    public static void checkApiLevel(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decor = activity.getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            return;
        }
        Window window = activity.getWindow();
        window.setStatusBarColor(Color.parseColor("#D5D5D5"));
    }

    public static void setImageFromUrl(Context context, CircleImageView container, String url){
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .into(container);
    }

    public static String getCurrentUser(String option){
        String response = "null";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            switch (option) {
                case "photoUrl":
                    response = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "https://www.cornwallbusinessawards.co.uk/wp-content/uploads/2017/11/dummy450x450.jpg";
                    break;


                case "displayName" :
                    response = user.getDisplayName();
                    break;

                case "userUid" :
                    response = user.getUid();
                    break;

                case "userEmail" :
                    response = user.getEmail();
                    break;
            }

        }

        return response;
    }
}
