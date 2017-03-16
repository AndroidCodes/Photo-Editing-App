package com.example.androidcodes.photoeditingapp.flycoDialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.androidcodes.photoeditingapp.R;
import com.flyco.animation.FlipEnter.FlipHorizontalEnter;
import com.flyco.dialog.widget.base.BottomBaseDialog;


public class ShareBottomDialog extends BottomBaseDialog<ShareBottomDialog> {
    LinearLayout camera;
    LinearLayout gallery;


    public ShareBottomDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public ShareBottomDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        showAnim(new FlipHorizontalEnter());
        dismissAnim(null);
        View inflate = View.inflate(mContext, R.layout.dialog_share, null);
        camera = ViewFindUtils.find(inflate, R.id.ll_camera);
        gallery = ViewFindUtils.find(inflate, R.id.ll_gallery);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
    }
}
