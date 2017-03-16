package com.example.androidcodes.photoeditingapp.Frames.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidcodes.photoeditingapp.R;
import com.example.androidcodes.photoeditingapp.TouchImageView.TouchImageView;

import java.util.ArrayList;

/**
 * Created by peacock on 11/4/16.
 */
public class MyAlbum_ViewPager_Adapter extends PagerAdapter {

    private Context context;

    private ArrayList<Bitmap> imagePaths;

    private LayoutInflater inflater;

    public MyAlbum_ViewPager_Adapter(Context context,
                                     ArrayList<Bitmap> imagePaths) {

        this.context = context;

        this.imagePaths = imagePaths;

        inflater = (LayoutInflater) context.getSystemService(Context.
                LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return imagePaths.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view == (View) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.my_album_items, container, false);

        final TouchImageView tiv_albumImage = (TouchImageView) view.
                findViewById(R.id.tiv_albumImage);

        tiv_albumImage.setImageBitmap(imagePaths.get(position));

       /* Common.imageLoader.displayImage("file://" + imagePaths.get(position), tiv_albumImage,
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                        tiv_albumImage.setImageResource(R.drawable.no_image_available);

                        super.onLoadingStarted(imageUri, view);

                    }
                });*/

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);

    }
}
