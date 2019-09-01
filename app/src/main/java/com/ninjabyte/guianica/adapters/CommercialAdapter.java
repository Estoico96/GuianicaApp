package com.ninjabyte.guianica.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.model.Commercial;

import java.util.ArrayList;

public class CommercialAdapter extends PagerAdapter {
    private Context context;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Commercial> arrayCommercials;

    public CommercialAdapter(Context context, Activity activity, ArrayList<Commercial> arrayCommercials) {
        this.arrayCommercials = arrayCommercials;
        this.context = context;
        this.activity = activity;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public int getCount() {
        return arrayCommercials != null ? arrayCommercials.size() : 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final View itemView = inflater.inflate(R.layout.view_fragment_commercial, container, false);
        ImageView banner = itemView.findViewById(R.id.banner_fragment_commercial);
        TextView companyName = itemView.findViewById(R.id.company_name_fragment_commercial);
        TextView companyTelephone = itemView.findViewById(R.id.company_telephone_fragment_commercial);
        TextView companyDescription = itemView.findViewById(R.id.company_description_fragment_commercial);
        ImageButton call = itemView.findViewById(R.id.btn_call_fragment_commercial);
        companyName.setText(arrayCommercials
                .get(position)
                .getName());

        companyDescription.setText(arrayCommercials
                .get(position)
                .getDescription());

        companyTelephone.setText(arrayCommercials
                .get(position)
                .getNumber());

        Utilities.setImageFromUrl(context, Utilities.TYPE_NORMAL, null, banner, arrayCommercials
                .get(position)
                .getUrl());

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = arrayCommercials.get(position).getNumber();
                if (!TextUtils.isEmpty(phoneNumber)) {
                    String telephone = "tel:" + phoneNumber;
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(telephone)));
                } else {
                    Utilities.showSnackBar(itemView.findViewById(R.id.container_main_home_fragment), "Número desconocido.");
                }
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }

}
