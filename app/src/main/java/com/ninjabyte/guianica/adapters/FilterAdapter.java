package com.ninjabyte.guianica.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ninjabyte.guianica.R;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ResultAdapter resultAdapter;
    private ArrayList<String> arrayFilters;



    public FilterAdapter(Context context, ArrayList<String> arrayFilters, ResultAdapter resultAdapter) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.resultAdapter = resultAdapter;
        this.arrayFilters = arrayFilters;
    }

    @NonNull
    @Override
    public FilterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_filter, viewGroup, false);
        return new FilterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterHolder filterHolder, int position) {
        filterHolder.type.setText(arrayFilters.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayFilters == null ? 0 : arrayFilters.size();
    }


    public class FilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView type;

        public FilterHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type_view_filter);
            type.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.type_view_filter) {
                resultAdapter.getFilter().filter(type.getText());
            }
        }

        private void selectButton(){

        }
    }
}
