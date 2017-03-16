package com.example.androidcodes.photoeditingapp.Frames.CustomGallery.NewAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidcodes.photoeditingapp.R;

import java.util.ArrayList;

/**
 * Created by peacock on 28/6/16.
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.CustomHolder> {

    private Context context;

    private ArrayList<String> imagesPaths;

    private int[] frameIds;

    private TextView tv_selectedImgCount;

    private OnImageClick OnImageClick;

    public ImagesAdapter(Context context, ArrayList<String> imagesPaths/*, int[] frameIds,
                         TextView tv_selectedImgCount*/) {

        this.context = context;

        this.imagesPaths = imagesPaths;

       /* this.frameIds = frameIds;

        this.tv_selectedImgCount = tv_selectedImgCount;*/

    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CustomHolder(LayoutInflater.from(context).
                inflate(R.layout.new_images_adapter_view, parent, false));

    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {

        Glide.with(context).load(imagesPaths.get(position)).
                placeholder(R.drawable.noimagefound).into(holder.aciv_albumImages);

    }

    @Override
    public int getItemCount() {

        return imagesPaths.size();

    }

    public void setOnImageClickListener(OnImageClick OnImageClick) {

        this.OnImageClick = OnImageClick;

    }

    public interface OnImageClick {

        public void OnImageClick(String selectedImgPath);

    }

    public class CustomHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatImageView aciv_albumImages, img_photo, img_delete;

        // RelativeLayout rl_selection;

        public CustomHolder(View convertView) {
            super(convertView);

            aciv_albumImages = (AppCompatImageView) convertView.findViewById(R.id.aciv_albumImages);
            aciv_albumImages.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (OnImageClick != null) {

                OnImageClick.OnImageClick(imagesPaths.get(getAdapterPosition()));

            }
        }
    }
}
