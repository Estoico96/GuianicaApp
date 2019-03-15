package com.ninjabyte.guianica.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.main.DetailOfferActivity;
import com.ninjabyte.guianica.model.Offer;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> arrayImages;


    public GalleryAdapter(Context context, ArrayList<String> arrayImages) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.arrayImages = arrayImages;
    }

    @NonNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_gallery_profile_activity, viewGroup, false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryHolder galleryHolder, int i) {
        galleryHolder.createOffer(i);
    }

    @Override
    public int getItemCount() {
        return arrayImages == null ? 0 : arrayImages.size();
    }

    public class GalleryHolder extends RecyclerView.ViewHolder {
        private CardView container;
        private ImageView image;

        public GalleryHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container_image_view_gallery_profile_activity);
            image = itemView.findViewById(R.id.image_view_gallery_profile_activity);
        }

        public void createOffer(int position) {

            Utilities.setImageFromUrl(context,Utilities.TYPE_NORMAL, null, image, arrayImages.get(position));

            if (position == arrayImages.size() - 1) {
                Utilities.setMargins(container, 0, 0, 50, 0, "END");
            }
        }

    }
}
