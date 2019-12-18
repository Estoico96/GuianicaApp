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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.main.DetailOfferActivity;
import com.ninjabyte.guianica.model.FeaturedProduct;
import com.ninjabyte.guianica.model.Offer;

import java.util.ArrayList;

public class FeaturedProductsAdapter extends RecyclerView.Adapter<FeaturedProductsAdapter.FeaturedProductsHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<FeaturedProduct> arrayFeaturedProducts;


    public FeaturedProductsAdapter(Context context, ArrayList<FeaturedProduct> arrayFeaturedProducts) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.arrayFeaturedProducts = arrayFeaturedProducts;
    }

    @NonNull
    @Override
    public FeaturedProductsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_featured_products, viewGroup, false);
        return new FeaturedProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeaturedProductsHolder featuredProductsHolder, int i) {
       featuredProductsHolder.createFeaturedProducts(i);
    }

    @Override
    public int getItemCount() {
        return arrayFeaturedProducts == null ? 0 : arrayFeaturedProducts.size();
    }

    public class FeaturedProductsHolder extends RecyclerView.ViewHolder {
        private CardView container;
        private TextView name;
        private TextView description;
        private TextView price;
        private ImageView image;

        public FeaturedProductsHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container_view_featured_products);
            name = itemView.findViewById(R.id.name_view_featured_products);
            description = itemView.findViewById(R.id.description_view_featured_products);
            price = itemView.findViewById(R.id.price_featured_products);
            image = itemView.findViewById(R.id.image_view_featured_products);

        }

        public void createFeaturedProducts(int position) {

            name.setText(arrayFeaturedProducts.get(position).getName());
            description.setText(arrayFeaturedProducts.get(position).getDescription());
            price.setText(arrayFeaturedProducts.get(position).getPrice());

            Utilities.setImageFromUrl(context,Utilities.TYPE_NORMAL, null, image, arrayFeaturedProducts.get(position).getImage());

            if (position == arrayFeaturedProducts.size() - 1) {
                Utilities.setMargins(container, 0, 0, 50, 0, "END");
            }
        }

    }
}
