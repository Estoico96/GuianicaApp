package com.ninjabyte.guianica.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.model.DetailOffer;

import java.util.ArrayList;

public class DetailOfferAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DetailOffer> arrayDetailOffer;

    public DetailOfferAdapter(Context context,ArrayList<DetailOffer> arrayDetailOffer) {

        this.arrayDetailOffer = arrayDetailOffer;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.view_detail_offer, container, false);

        ImageView banner = view.findViewById(R.id.banner_detail_offer);
        Utilities.setImageFromUrl(context, Utilities.TYPE_NORMAL, null, banner, arrayDetailOffer.get(position).getBannerUrl());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView( (RelativeLayout) object);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return arrayDetailOffer.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
