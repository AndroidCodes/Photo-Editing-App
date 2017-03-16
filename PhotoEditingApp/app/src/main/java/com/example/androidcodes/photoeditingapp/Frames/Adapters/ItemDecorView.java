package com.example.androidcodes.photoeditingapp.Frames.Adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by peacock on 2/5/16.
 */
public class ItemDecorView extends RecyclerView.ItemDecoration {

    private int space;

    public ItemDecorView(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        //if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
       /* } else {
            outRect.top = 0;
        }*/
    }
}
