package com.example.androidcodes.photoeditingapp.Frames.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.androidcodes.photoeditingapp.R;

import java.util.ArrayList;

/**
 * Created by peacock on 26/3/16.
 */
public class Recycler_Adapter extends RecyclerView.
        Adapter<Recycler_Adapter.Holder> {

    private Context context;

    private ArrayList<String> selected_images_paths;

    private int[] frame_images_ids;

    private OnItemClickListener onItemClickListener;

    private GalleryAdapter adapter;

    public Recycler_Adapter(Context context, ArrayList<String> selected_images_paths,
                            int[] frame_images_ids, GalleryAdapter adapter) {

        this.context = context;

        this.selected_images_paths = selected_images_paths;

        this.frame_images_ids = frame_images_ids;

        if (selected_images_paths != null) {

            this.adapter = adapter;

        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycle_item,
                parent, false);

        return new Holder(view);

    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        if (selected_images_paths != null) {

            holder.imgQueueMultiSelected.setVisibility(View.VISIBLE);

            Glide.with(context).load(selected_images_paths.get(position)).centerCrop().
                    placeholder(R.drawable.noimagefound).crossFade().into(holder.iv_image);

            /*Common.imageLoader.displayImage("file://" +
                            selected_images_paths.get(position).toString(),
                    holder.iv_image,
                    new SimpleImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                            holder.iv_image.
                                    setImageResource(R.drawable.noimagefound);

                            super.onLoadingStarted(imageUri, view);
                        }
                    });*/

            holder.imgQueueMultiSelected.setTag(position);

            holder.imgQueueMultiSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    adapter.ChangeSelection(selected_images_paths.get((int) v.getTag()).toString());

                    selected_images_paths.remove((int) v.getTag());

                    notifyItemChanged((int) v.getTag());
                    notifyDataSetChanged();

                }
            });

        } else {

            holder.imgQueueMultiSelected.setVisibility(View.GONE);

            Glide.with(context).load(frame_images_ids[position]).centerCrop().
                    placeholder(R.drawable.noimagefound).crossFade().into(holder.iv_image);

            /*Common.imageLoader.displayImage("drawable://" +
                            frame_images_ids[position],
                    holder.iv_image,
                    new SimpleImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                            holder.iv_image.setImageResource(R.drawable.noimagefound);

                            super.onLoadingStarted(imageUri, view);

                        }
                    });*/
        }
    }

    @Override
    public int getItemCount() {

        if (selected_images_paths != null) {

            return selected_images_paths.size();

        } else {

            return frame_images_ids.length;

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {

        public void OnItemClick(View view, int position);

    }

    public class Holder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public ImageView iv_image, imgQueueMultiSelected;

        public Holder(View convertView) {
            super(convertView);

            iv_image = (ImageView) convertView.findViewById(R.id.iv_image);

            imgQueueMultiSelected = (ImageView) convertView.findViewById(R.id.imgQueueMultiSelected);

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
