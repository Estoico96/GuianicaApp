package com.ninjabyte.guianica.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.main.ProfileActivity;
import com.ninjabyte.guianica.main.ResultActivity;
import com.ninjabyte.guianica.model.Result;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> implements Filterable {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Result> arrayResults;
    private ArrayList<Result> arrayResultsFiltered;
    private RecyclerView recyclerResult;


    public ResultAdapter(Context context, ArrayList<Result> arrayResults, RecyclerView recyclerResult) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.arrayResults = arrayResults;
        this.arrayResultsFiltered = arrayResults;
        this.recyclerResult = recyclerResult;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_result, viewGroup, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder resultHolder, int position) {
        resultHolder.createResults(position);
    }

    @Override
    public int getItemCount() {
        return arrayResultsFiltered == null ? 0 : arrayResultsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    arrayResultsFiltered = arrayResults;
                } else {
                    ArrayList<Result> filteredList = new ArrayList<>();
                    filteredList.clear();
                    for (Result row : arrayResults) {
                        if (row.getSpecialty().contains(charString)) {
                            filteredList.add(row);
                        }
                    }
                    if (filteredList.size() == 0) {
                        filteredList = arrayResults;
                    }
                    arrayResultsFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayResultsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayResultsFiltered = (ArrayList<Result>) results.values;
                Utilities.runLayoutAnimation(recyclerResult, context);
                notifyDataSetChanged();
            }
        };
    }

    public class ResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageButton btnSendProfile;
        private TextView companyName;
        private CircleImageView companyLogo;
        private TextView companySpecialty;
        private TextView companySchedule;
        private RatingBar companyRating;
        private TextView companyRatingCount;
        private TextView companyStateDelivery;
        private TextView companyStateOffer;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);

            btnSendProfile = itemView.findViewById(R.id.button_send_profile_view_result);
            companyName = itemView.findViewById(R.id.company_name_view_result);
            companyLogo = itemView.findViewById(R.id.company_logo_view_result);
            companySchedule = itemView.findViewById(R.id.schedule_company_view_result);
            companyRating = itemView.findViewById(R.id.company_rating_view_result);
            companyRatingCount = itemView.findViewById(R.id.count_rating_view_result);
            companyStateDelivery = itemView.findViewById(R.id.state_delivery_view_result);
            companySpecialty = itemView.findViewById(R.id.company_specialty_view_result);
            companyStateOffer = itemView.findViewById(R.id.state_offer_company_view_result);

            btnSendProfile.setOnClickListener(this);
        }

        public void createResults(int position) {
            float rating = arrayResultsFiltered.get(position).getRating();
            String valueDelivery = arrayResultsFiltered.get(position).getDelivery().getValue();
            boolean activeDelivery = arrayResultsFiltered.get(position).getDelivery().isActive();

            Utilities.setImageFromUrl(context, Utilities.TYPE_CIRCLE, companyLogo, null, arrayResultsFiltered.get(position).getLogoUrl());
            companyName.setText(arrayResultsFiltered.get(position).getCompany());
            companySpecialty.setText(arrayResultsFiltered.get(position).getSpecialty());
            companySchedule.setText(String.format(
                    context.getResources().getString(
                            R.string.text_schedule_company_activity_result),
                    getSchedule(position, 0),
                    getSchedule(position, 1)
            ));
            companyRating.setRating(rating);
            companyRatingCount.setText(String.valueOf(rating));

            if (activeDelivery) {
                companyStateDelivery.setVisibility(View.VISIBLE);
                companyStateDelivery.setText(valueDelivery);
            } else {
                companyStateDelivery.setVisibility(View.GONE);
            }

            if (arrayResultsFiltered.get(position).isOffer()) {
                companyStateOffer.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button_send_profile_view_result) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("rsl_url_image", arrayResults.get(getAdapterPosition()).getLogoUrl());
                intent.putExtra("rsl_name", arrayResults.get(getAdapterPosition()).getCompany());
                intent.putExtra("rsl_specialty", arrayResults.get(getAdapterPosition()).getSpecialty());

                context.startActivity(intent);
            }
        }

        private String getSchedule(int position, int index) {
            return arrayResultsFiltered.get(position).getSchedule().get(index);
        }
    }
}
