package com.example.androidcodes.photoeditingapp.Frames.CustomGallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.Frames.CustomGallery.NewAdapters.AlbumAdapter;
import com.example.androidcodes.photoeditingapp.R;

import java.util.ArrayList;

/**
 * Created by peacock on 28/6/16.
 */
public class New_AlbumFragment extends BaseFragment implements AlbumAdapter.onItemClickListener {

    private Activity activity;

    private ArrayList<Albums> allAlbums;

    private AlbumAdapter albumAdapter;

    private View albumView;

    private RecyclerView rv_albums;

    private AppCompatTextView actv_noAlbumFound;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        activity = getActivity();

        albumView = inflater.inflate(R.layout.new_gallery_items, container, false);

        allAlbums = Common.allAlbums;

        rv_albums = (RecyclerView) albumView.findViewById(R.id.rv_albums);

        actv_noAlbumFound = (AppCompatTextView) albumView.findViewById(R.id.actv_noAlbumFound);

        initUI();

        return albumView;

    }

    @Override
    public void onResume() {
        super.onResume();

        allAlbums = Common.getAlbums(activity);

        initUI();

    }

    private void initUI() {

        if (allAlbums != null && allAlbums.size() > 0) {

            actv_noAlbumFound.setVisibility(View.GONE);

            albumAdapter = new AlbumAdapter(activity, Common.allAlbums);
            albumAdapter.setOnAlbumClickListener(this);

            rv_albums.setHasFixedSize(true);
            rv_albums.setLayoutManager(new LinearLayoutManager(activity,
                    GridLayoutManager.VERTICAL, false));
            //rv_albums.addItemDecoration(new ItemDecoration(3));
            rv_albums.setAdapter(albumAdapter);

        } else {

            actv_noAlbumFound.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onItemClick(int position, String albumName, ArrayList<String> allAlbumImages) {

        //TextView tv_headerText = (TextView) activity.findViewById(R.id.tv_headerText);

        //Common.selectedAlbum = position;

        customGallery.getHeaderText().setText(albumName);

        New_AlbumImagesFragment aif = new New_AlbumImagesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        aif.setArguments(bundle); //setArguments and getArguments are default method for fragments.

        (((FragmentActivity) activity).getSupportFragmentManager()).beginTransaction().
                replace(R.id.fl_gallery, aif, "listImages").addToBackStack(null).commit();

    }
}
