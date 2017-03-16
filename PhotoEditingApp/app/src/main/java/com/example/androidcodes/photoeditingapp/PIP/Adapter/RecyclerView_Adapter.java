package com.example.androidcodes.photoeditingapp.PIP.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidcodes.photoeditingapp.PIP.Common.PIP_Common;
import com.example.androidcodes.photoeditingapp.R;


/**
 * Created by peacock on 2/6/16.
 */
public class RecyclerView_Adapter extends RecyclerView.
        Adapter<RecyclerView_Adapter.CustomViewHolder> {

    private Context context;

    private int images[];

    private int seleted = -1;

    private String[] effectName;

    private OnItemClickListener mItemClickListener;

    public RecyclerView_Adapter(Context context, int[] images, String[] effectName) {

        this.context = context;

        this.images = images;

        this.effectName = effectName;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pip_recyclerview_item, parent,
                false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        if (effectName == null) {

            holder.tv_effectName.setVisibility(View.GONE);

        } else {

            holder.tv_effectName.setVisibility(View.VISIBLE);

            holder.tv_effectName.setText(effectName[position].toString());

        }

        if (((PIP_Common.selectedFrame == position) && (PIP_Common.catagory.equals("frames")))
                || ((PIP_Common.selectedForeground == position) &&
                (PIP_Common.catagory.equals("foreground"))) ||
                ((PIP_Common.selectedBackground == position) &&
                        (PIP_Common.catagory.equals("background")))) {

            holder.iv_recyclerImg.setBackgroundResource(R.drawable.selector);

        } /*else if ((PIP_Common.selectedForeground == position) &&
                (PIP_Common.catagory.equals("foreground"))) {

            holder.iv_recyclerImg.setBackgroundResource(R.drawable.selector);

        } else if ((PIP_Common.selectedBackground == position) &&
                (PIP_Common.catagory.equals("background"))) {

            holder.iv_recyclerImg.setBackgroundResource(R.drawable.selector);

        }*/ else {

            holder.iv_recyclerImg.setBackgroundResource(R.drawable.selector_blank);

        }

        Glide.with(context).load(images[position]).centerCrop().
                placeholder(R.drawable.noimagefound).crossFade().into(holder.iv_recyclerImg);
        /*Common.imageLoader.displayImage("drawable://" + images[position], holder.iv_recyclerImg,
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                        super.onLoadingStarted(imageUri, view);

                    }
                });*/
    }

    @Override
    public int getItemCount() {

        return images.length;

    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }

    public void setSelected(int position) {

        if (PIP_Common.catagory.equals("frames")) {

            PIP_Common.selectedFrame = position;

        } else if (PIP_Common.catagory.equals("foreground")) {

            PIP_Common.selectedForeground = position;

        } else if (PIP_Common.catagory.equals("background")) {

            PIP_Common.selectedBackground = position;

        }

        notifyDataSetChanged();

    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView iv_recyclerImg;

        public TextView tv_effectName;

        public CustomViewHolder(View convertView) {
            super(convertView);

            iv_recyclerImg = (ImageView) convertView.findViewById(R.id.iv_recyclerImg);

            tv_effectName = (TextView) convertView.findViewById(R.id.tv_effectName);

            convertView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {

                mItemClickListener.onItemClick(v, getLayoutPosition());

            }
        }
    }
}
