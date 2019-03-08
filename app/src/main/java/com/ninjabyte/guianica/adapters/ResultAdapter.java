package com.ninjabyte.guianica.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.main.ProfileActivity;
import com.ninjabyte.guianica.model.Result;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Result> arrayResults;


    public ResultAdapter(Context context, ArrayList<Result> arrayResults) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.arrayResults = arrayResults;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_result, viewGroup, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder resultHolder, int i) {
        resultHolder.createResults(i);
    }

    @Override
    public int getItemCount() {
        return arrayResults == null ? 0 : arrayResults.size();
    }

    public class ResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView container;
        private ImageButton btnMore;
        private TextView name;
        private CircleImageView logo;
        private TextView specialty;
        private TextView state;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container_view_offer);
            name = itemView.findViewById(R.id.company_name_result_activity);
            logo = itemView.findViewById(R.id.company_logo_result_activity);
            specialty = itemView.findViewById(R.id.company_specialty_result_activity);
            state = itemView.findViewById(R.id.state_company_result_activity);
            btnMore = itemView.findViewById(R.id.btn_more_result_activity);


            btnMore.setOnClickListener(this);
        }

        public void createResults(int position) {
            name.setText(arrayResults.get(position).getCompany());
            specialty.setText(arrayResults.get(position).getSpecialty());
            Utilities.setImageFromUrl(context, Utilities.TYPE_CIRCLE, logo, null, arrayResults.get(position).getLogoUrl());

            Log.v("asdfg", " "+arrayResults.get(position).getLogoUrl());
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_more_result_activity){
                context.startActivity(new Intent(context, ProfileActivity.class));
            }
        }
    }
}
