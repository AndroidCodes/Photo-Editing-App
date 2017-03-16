package com.example.androidcodes.photoeditingapp.Frames.CustomGallery.NewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.R;

import java.util.ArrayList;

/**
 * Created by peacock on 28/6/16.
 */
public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.CustomHolder> {

    private Context context;

    private ArrayList<String> imagesPaths;

    private int[] frameIds;

    private TextView tv_selectedImgCount;

    private OnItemClickListener onItemClickListener = null;

    public SelectedImageAdapter(Context context, ArrayList<String> imagesPaths,
                                int[] frameIds, TextView tv_selectedImgCount) {

        this.context = context;

        this.imagesPaths = imagesPaths;

        this.frameIds = frameIds;

        this.tv_selectedImgCount = tv_selectedImgCount;

    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CustomHolder(LayoutInflater.from(context).inflate(R.layout.item_photo_select,
                parent, false));

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {

        if (imagesPaths == null) {

            holder.aciv_delete.setVisibility(View.GONE);

            Glide.with(context).load(frameIds[position]).centerCrop().crossFade().
                    placeholder(R.drawable.noimagefound).into(holder.aciv_images);

        } else {

            holder.aciv_delete.setVisibility(View.VISIBLE);

            Glide.with(context).load(imagesPaths.get(position)).centerCrop().crossFade().
                    placeholder(R.drawable.noimagefound).into(holder.aciv_images);

        }
    }

    @Override
    public int getItemCount() {

        if (imagesPaths == null) {

            return frameIds.length;

        } else {

            tv_selectedImgCount.setText(context.getString(R.string.selecteImgs) + " " + imagesPaths.size());

            return imagesPaths.size();

        }
    }

    public interface OnItemClickListener {

        public void OnItemClick(View view, int position);

    }

    public class CustomHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView aciv_images, aciv_delete;

        public CustomHolder(View convertView) {
            super(convertView);

            aciv_images = (ImageView) convertView.findViewById(R.id.aciv_images);

            aciv_delete = (ImageView) convertView.findViewById(R.id.aciv_delete);
            aciv_delete.setOnClickListener(this);

            if (onItemClickListener != null) {

                convertView.setOnClickListener(this);

            }
        }

        @Override
        public void onClick(View v) {

            if (onItemClickListener == null) {

                Common.selected.remove(getAdapterPosition());

                //notifyItemRemoved(getAdapterPosition());

                notifyDataSetChanged();

            } else {

                onItemClickListener.OnItemClick(v, getAdapterPosition());

            }
        }
    }
}
