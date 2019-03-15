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

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Offer> arrayOffers;


    public OfferAdapter(Context context, ArrayList<Offer> arrayOffers) {
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
    public void onBindViewHolder(@NonNull final OfferHolder offerHolder, int i) {
        offerHolder.createOffer(i);

        offerHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailOfferActivity.class);
                Bundle data = new Bundle();
                data.putString("uidOft", arrayOffers.get(offerHolder.getAdapterPosition()).getUid());
                data.putString("nameOft", arrayOffers.get(offerHolder.getAdapterPosition()).getCompany());
                intent.putExtras(data);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayOffers == null ? 0 : arrayOffers.size();
    }

    public class OfferHolder extends RecyclerView.ViewHolder {
        private CardView container;
        private TextView tooltip;
        private ImageView banner;

        public OfferHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container_view_offer);
            banner = itemView.findViewById(R.id.banner_view_offer);
            tooltip = itemView.findViewById(R.id.tooltip_view_offer);

        }

        public void createOffer(int position) {

            String counter = "+" + arrayOffers.get(position).getCounter();
            Utilities.setImageFromUrl(context, Utilities.TYPE_NORMAL, null, banner, arrayOffers.get(position).getLastBannerUrlOffer());
            tooltip.setText(counter);

            if (position == arrayOffers.size() - 1) {
                Utilities.setMargins(container, 0, 0, 50, 0, "END");
            }
        }

    }
}
