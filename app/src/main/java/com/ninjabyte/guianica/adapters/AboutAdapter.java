package com.ninjabyte.guianica.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ninjabyte.guianica.R;
import com.ninjabyte.guianica.Utilities;
import com.ninjabyte.guianica.model.About;

import java.util.ArrayList;

public class AboutAdapter  extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<About> abouts;

    public AboutAdapter(Context context) {
        this.context = context;
        abouts = Utilities.getAboutContent(context);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return abouts.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.view_about, container, false);

        About about = abouts.get(position);

        ImageView image = view.findViewById(R.id.image_illustration_view_about);
        TextView title = view.findViewById(R.id.title_about);
        TextView description = view.findViewById(R.id.description_about);

        image.setImageDrawable(about.getImage());
        title.setText(about.getTitle());
        description.setText(about.getDescription());

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }


}
