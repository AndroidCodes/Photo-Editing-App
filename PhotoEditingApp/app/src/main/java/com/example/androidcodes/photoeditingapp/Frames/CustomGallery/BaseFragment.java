package com.example.androidcodes.photoeditingapp.Frames.CustomGallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by peacock on 29/6/16.
 */
public class BaseFragment extends Fragment {

    public New_CustomGallery customGallery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customGallery = (New_CustomGallery) getActivity();

    }
}
