package com.ninjabyte.guianica;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ninjabyte.guianica.model.About;

import java.util.ArrayList;

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

}
