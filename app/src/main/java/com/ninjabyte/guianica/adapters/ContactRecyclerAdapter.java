package com.ninjabyte.guianica.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.model.Telephone;

import java.util.ArrayList;

public class ContactRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    private int viewType;
    private ArrayList<Telephone> arrayTelephones;
    private ArrayList<String> arrayLocations;

    public ContactRecyclerAdapter(Context context, ArrayList<Telephone> arrayTelephones, ArrayList<String> arrayLocations, int viewType) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.viewType = viewType;

        this.arrayTelephones = arrayTelephones;
        this.arrayLocations = arrayLocations;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;

        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.view_telephones, viewGroup, false);
                return new CallRecyclerHolder(view);

            case 2:
                view = inflater.inflate(R.layout.view_locations, viewGroup, false);
                return new LocationRecyclerHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewType) {

            case 1:

                ((CallRecyclerHolder) viewHolder).place.setText(arrayTelephones.get(position).getPlace());
                ((CallRecyclerHolder) viewHolder).number.setText(arrayTelephones.get(position).getNumber());

                break;

            case 2:

                ((LocationRecyclerHolder) viewHolder).place.setText(arrayLocations.get(position));

                break;

        }

    }


    @Override
    public int getItemCount() {
        int currentSize = 0;

        switch (viewType){
            case 1 :
                currentSize = arrayTelephones.size();
                break;

            case 2 :
                currentSize = arrayLocations.size();
                break;
        }

        return currentSize;
    }

    public static class CallRecyclerHolder extends RecyclerView.ViewHolder {

        TextView place;
        TextView number;
        ImageView btnCall;

        public CallRecyclerHolder(@NonNull View itemView) {
            super(itemView);

            place = itemView.findViewById(R.id.place_view_telephones);
            number = itemView.findViewById(R.id.number_view_telephone);
            btnCall = itemView.findViewById(R.id.btn_call_view_telephone);
        }
    }


    public static class LocationRecyclerHolder extends RecyclerView.ViewHolder {

        TextView place;
        ImageView btnSeeMap;

        public LocationRecyclerHolder(@NonNull View itemView) {
            super(itemView);

            place = itemView.findViewById(R.id.place_view_telephone);
            btnSeeMap = itemView.findViewById(R.id.btn_see_map_view_telephone);
        }
    }

}
