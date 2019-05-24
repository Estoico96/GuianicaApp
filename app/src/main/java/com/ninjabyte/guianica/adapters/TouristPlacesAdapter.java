package com.ninjabyte.guianica.adapters;

import android.content.Context;
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
import com.ninjabyte.guianica.model.TouristPlace;

import java.util.ArrayList;

public class TouristPlacesAdapter extends RecyclerView.Adapter<TouristPlacesAdapter.TouristPlacesHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<TouristPlace> arrayTouristPlaces;


    public TouristPlacesAdapter(Context context, ArrayList<TouristPlace> arrayTouristPlaces) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.arrayTouristPlaces = arrayTouristPlaces;
    }

    @NonNull
    @Override
    public TouristPlacesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_tourist_places, viewGroup, false);
        return new TouristPlacesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TouristPlacesHolder touristPlacesHolder, int i) {
        touristPlacesHolder.createTouristPlacesHolder(i);
    }

    @Override
    public int getItemCount() {
        return arrayTouristPlaces == null ? 0 : arrayTouristPlaces.size();
    }

    public class TouristPlacesHolder extends RecyclerView.ViewHolder {
        private CardView container;
        private ImageView banner;
        private TextView name;

        public TouristPlacesHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container_view_tourist_places);
            banner = itemView.findViewById(R.id.banner_view_tourist_places);
            name = itemView.findViewById(R.id.title_view_tourist_places);
        }

        public void createTouristPlacesHolder(int position) {

            Utilities.setImageFromUrl(context, Utilities.TYPE_NORMAL, null, banner, arrayTouristPlaces.get(position).getBanner());
            name.setText(arrayTouristPlaces.get(position).getName());

            if (position == arrayTouristPlaces.size() - 1) {
                Utilities.setMargins(container, 0, 0, 50, 0, "END");
            }
        }

    }
}
