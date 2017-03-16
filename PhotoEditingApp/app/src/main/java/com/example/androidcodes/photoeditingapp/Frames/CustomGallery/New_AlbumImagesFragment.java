package com.example.androidcodes.photoeditingapp.Frames.CustomGallery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.Frames.CustomGallery.NewAdapters.ImagesAdapter;
import com.example.androidcodes.photoeditingapp.R;

import java.util.ArrayList;

/**
 * Created by peacock on 28/6/16.
 */
public class New_AlbumImagesFragment extends Fragment implements ImagesAdapter.OnImageClick {

    private int position;

    private ArrayList<String> listImages;

    private Activity activity;

    private ImagesAdapter adapter;

    private OnItemClickListener onItemClickListener;

    private View gallery;

    private RecyclerView rv_albumImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        gallery = inflater.inflate(R.layout.new_album_images, container, false);

        activity = getActivity();

        rv_albumImages = (RecyclerView) gallery.findViewById(R.id.rv_albumImages);

        initUI();

        return gallery;

    }

    private void initUI() {

        position = getArguments().getInt("position", 0);

        listImages = Common.getAlbumImages(activity,
                Common.allAlbums.get(position).getAlbumName());

        if (listImages != null && listImages.size() > 0) {

            adapter = new ImagesAdapter(activity, listImages);
            adapter.setOnImageClickListener(this);

            rv_albumImages.setHasFixedSize(true);
            rv_albumImages.setLayoutManager(new GridLayoutManager(activity, 3,
                    GridLayoutManager.VERTICAL, false));
            //rv_albumImages.addItemDecoration(new ItemDecoration(4));
            rv_albumImages.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        initUI();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        onItemClickListener = (OnItemClickListener) context;

    }

    @Override
    public void OnImageClick(String selectedImgPath) {

        onItemClickListener.onItemClick(selectedImgPath);

    }

    public interface OnItemClickListener {

        void onItemClick(String selectedImagePath);

    }
}
