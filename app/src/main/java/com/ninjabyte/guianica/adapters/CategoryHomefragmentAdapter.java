package com.ninjabyte.guianica.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.ninjabyte.guianica.ResultActivity;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.model.Category;
import com.ninjabyte.guianica.model.Offer;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryHomefragmentAdapter extends RecyclerView.Adapter<CategoryHomefragmentAdapter.CategoryHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Category> arrayCategories;


    public CategoryHomefragmentAdapter(Context context, ArrayList<Category> arrayCategories) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.arrayCategories = arrayCategories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_category, viewGroup, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int i) {
        categoryHolder.onCreateCategory(i);
    }

    @Override
    public int getItemCount() {
        return arrayCategories == null ? 0 : arrayCategories.size();
    }




    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView container;
        private TextView category;


        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container_view_category);
            category = itemView.findViewById(R.id.name_view_category);

            container.setOnClickListener(this);
        }

        public void onCreateCategory(int position) {

            category.setText(arrayCategories
                    .get(position)
                    .getName());

            category.setBackgroundColor(Color
                    .parseColor(arrayCategories
                            .get(position)
                            .getColor()));

            if (position == arrayCategories.size() - 1) {
                Utilities.setMargins(container, 0, 0, 50 , 0, "END");
            }
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.container_view_category){
                Intent data = new Intent(context, ResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("category_name", category.getText().toString());
                data.putExtras(bundle);
                context.startActivity(data);
            }
        }
    }
}
