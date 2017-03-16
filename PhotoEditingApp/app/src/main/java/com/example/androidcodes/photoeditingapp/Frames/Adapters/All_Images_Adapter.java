package com.example.androidcodes.photoeditingapp.Frames.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.androidcodes.photoeditingapp.R;

import java.util.ArrayList;

public class All_Images_Adapter extends RecyclerView.Adapter<All_Images_Adapter.CustomHolder> {

    private Context context;

    private ArrayList<Bitmap> images_paths;

    private OnItemClickListener onItemClickListener;

    public All_Images_Adapter(Context context, ArrayList<Bitmap> images_paths) {

        this.context = context;

        this.images_paths = images_paths;

    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.all_images_items,
                parent, false);

        return new CustomHolder(view);

    }

    @Override
    public void onBindViewHolder(final CustomHolder holder, int position) {

        holder.iv_allImgs.setImageBitmap(images_paths.get(position));

        /*Common.imageLoader.displayImage("file://" + images_paths.get(position), holder.iv_allImgs,
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                        holder.iv_allImgs.setImageResource(R.drawable.no_image_available);

                        super.onLoadingStarted(imageUri, view);

                    }
                });*/
    }

    @Override
    public int getItemCount() {

        return images_paths.size();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {

        public void OnItemClick(View view, int position);

    }

    public class CustomHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public ImageView iv_allImgs;

        public CustomHolder(View convertView) {
            super(convertView);

            iv_allImgs = (ImageView) convertView.findViewById(R.id.iv_allImg);

            convertView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (onItemClickListener != null) {

                onItemClickListener.OnItemClick(view, getPosition());

            }
        }
    }
}