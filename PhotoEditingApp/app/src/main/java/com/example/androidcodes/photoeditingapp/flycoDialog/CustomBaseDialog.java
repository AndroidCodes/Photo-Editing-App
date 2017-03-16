package com.example.androidcodes.photoeditingapp.flycoDialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.androidcodes.photoeditingapp.R;
import com.flyco.animation.Attention.Swing;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

public class CustomBaseDialog extends BaseDialog<CustomBaseDialog> {
    private TextView btn_cancel;
    private TextView btn_exit;

    public CustomBaseDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new Swing());

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.exit_app_dialog, null);
        btn_exit = ViewFindUtils.find(inflate, R.id.btn_exit);
        btn_cancel = ViewFindUtils.find(inflate, R.id.btn_cancel);
        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }


}
