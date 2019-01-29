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
import android.widget.TextView;

import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.model.Notice;

import java.util.ArrayList;

public class NoticeAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Notice> arrayNotices;

    public NoticeAdapter(Context context, ArrayList<Notice> arrayNotices) {
        this.arrayNotices = arrayNotices;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public int getCount() {
        return arrayNotices.size();//arrayNotices != null ? arrayNotices.size() : 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.fragment_notice, container, false);

        ImageView image = itemView.findViewById(R.id.image_fragment_notice);
        TextView title = itemView.findViewById(R.id.title_fragment_notice);

            title.setText(arrayNotices
                    .get(position)
                    .getTitle());

            Utilities.setImageFromUrl(context, Utilities.TYPE_NORMAL, null, image, arrayNotices
                    .get(position)
                    .getUrlImage());


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}
