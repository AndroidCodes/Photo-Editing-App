package com.example.androidcodes.photoeditingapp.Frames.CustomGallery.NewAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.androidcodes.photoeditingapp.Frames.CustomGallery.Albums;
import com.example.androidcodes.photoeditingapp.R;

import java.util.ArrayList;

/**
 * Created by peacock on 28/6/16.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.CustomHolder> {

    private Context context;

    private ArrayList<Albums> listAlbums;

    private onItemClickListener onItemClickListener;

    public AlbumAdapter(Context context, ArrayList<Albums> listAlbums) {

        this.context = context;

        this.listAlbums = listAlbums;

    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CustomHolder(LayoutInflater.from(context).
                inflate(R.layout.new_album_adapter_view, parent, false));

    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {

        Glide.with(context).load(listAlbums.get(position).getAlbumImgPath()).centerCrop().
                placeholder(R.drawable.noimagefound).crossFade().into(holder.aciv_albumImg);

        holder.aciv_albumName.setText(listAlbums.get(position).getAlbumName() + "(" +
                listAlbums.get(position).getAlbumImgCount() + ")");

    }

    @Override
    public int getItemCount() {

        return listAlbums.size();

    }

    public void setOnAlbumClickListener(onItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;

    }

    public interface onItemClickListener {

        public void onItemClick(int position, String albumName, ArrayList<String> allAlbumImages);

    }

    public class CustomHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatImageView aciv_albumImg;

        public AppCompatTextView aciv_albumName;

        public CustomHolder(View convertView) {
            super(convertView);

            aciv_albumImg = (AppCompatImageView) convertView.findViewById(R.id.aciv_albumImg);

            aciv_albumName = (AppCompatTextView) convertView.findViewById(R.id.aciv_albumName);

            convertView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (onItemClickListener != null) {

                onItemClickListener.onItemClick(getAdapterPosition(),
                        listAlbums.get(getAdapterPosition()).getAlbumName(),
                        listAlbums.get(getAdapterPosition()).getAllAlbumImgs());

            }
        }
    }
}
