package com.ninjabyte.guianica.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.model.Offer;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfferHomefragmentAdapter extends RecyclerView.Adapter<OfferHomefragmentAdapter.OfferHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Offer> arrayOffers;


    public OfferHomefragmentAdapter(Context context, ArrayList<Offer> arrayOffers) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.arrayOffers = arrayOffers;
    }

    @NonNull
    @Override
    public OfferHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_offer, viewGroup, false);
        return new OfferHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferHolder offerHolder, int i) {
        offerHolder.createOffer(i);
    }

    @Override
    public int getItemCount() {
        return arrayOffers == null ? 0 : arrayOffers.size();
    }

    public class OfferHolder extends RecyclerView.ViewHolder {
        private CardView container;
        private TextView tooltip;
        private ImageView banner;
        private CircleImageView logo;
        private TextView companyName;


        public OfferHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container_view_offer);
            banner = itemView.findViewById(R.id.banner_view_offer);
            tooltip = itemView.findViewById(R.id.tooltip_view_offer);
            logo = itemView.findViewById(R.id.company_logo_view_offer);
            companyName = itemView.findViewById(R.id.company_name_view_offer);
        }

        public void createOffer(int position) {

            String counter = "+" + arrayOffers.get(position).getCounter();
            Utilities.setImageFromUrl(context, Utilities.TYPE_CIRCLE, logo, null, arrayOffers.get(position).getLogoUrl());
            Utilities.setImageFromUrl(context, Utilities.TYPE_NORMAL, null, banner, arrayOffers.get(position).getLastBannerUrlOffer());
            companyName.setText(arrayOffers.get(position).getCompany());
            Log.v("asdf", "tamaño " + counter);
            tooltip.setText(counter);

            if (position == arrayOffers.size() - 1) {
                Utilities.setMargins(container, 0, 0, 50 , 0, "END");
            }
        }
    }
}
