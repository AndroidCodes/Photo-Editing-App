package com.example.androidcodes.photoeditingapp.Frames.CustomGallery;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by peacock on 28/6/16.
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {

    int spacing;

    public ItemDecoration(int spacing) {

        if (spacing < 3) {

            this.spacing = 3;

        } else {

            this.spacing = spacing;

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.set(spacing, spacing, spacing, spacing);

    }
}
