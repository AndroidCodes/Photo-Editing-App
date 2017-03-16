package com.example.androidcodes.photoeditingapp.Frames.EditImages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.Frames.Fragments.Fragement_Activity;
import com.example.androidcodes.photoeditingapp.R;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

/**
 * Created by peacock on 29/3/16.
 */
public class Edit_Images extends Activity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, Animation.AnimationListener {

    //private boolean animateHSV = false;

    private String effectName = "";

    private Bitmap bmp_original = null;

    private GPUImageFilter filter = null;

    private GPUImageFilterTools.FilterAdjuster mFilterAdjuster = null;

    private Intent intent;

    //==============================================================================================

    private Animation zoomIn/*, hsv_zoomOut, ll_slideUp, ll_slideDown*/;

    //==============================================================================================

    private TextView tv_headerText, tv_animEffect;

    private GPUImageView gv_image;

    private SeekBar sb_seekEffect;

    private ImageView iv_next, iv_imgEffectIcon;

    private HorizontalScrollView hsv_scrollEffcts;

//    private LinearLayout ll_doEffect;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_images);

        zoomIn = AnimationUtils.loadAnimation(Edit_Images.this, R.anim.zoom_in);
        zoomIn.setAnimationListener(this);

        /*hsv_zoomOut = AnimationUtils.loadAnimation(Edit_Images.this, R.anim.zoom_out);
        hsv_zoomOut.setAnimationListener(this);

        ll_slideUp = AnimationUtils.loadAnimation(Edit_Images.this, R.anim.slide_up_in);
        ll_slideUp.setAnimationListener(this);

        ll_slideDown = AnimationUtils.loadAnimation(Edit_Images.this, R.anim.slide_down_in);
        ll_slideDown.setAnimationListener(this);*/

        bmp_original = Common.edit_selected_framed_image;

        tv_headerText = (TextView) findViewById(R.id.tv_headerText);
        tv_headerText.setText("Edit Image");

        tv_animEffect = (TextView) findViewById(R.id.tv_animEffect);

        gv_image = (GPUImageView) findViewById(R.id.gv_image);
        gv_image.setImage(bmp_original);

        iv_imgEffectIcon = (ImageView) findViewById(R.id.iv_imgEffectIcon);

        sb_seekEffect = (SeekBar) findViewById(R.id.sb_seekEffect);
        sb_seekEffect.setMax(50);
        sb_seekEffect.setOnSeekBarChangeListener(this);

        hsv_scrollEffcts = (HorizontalScrollView) findViewById(R.id.hsv_scrollEffcts);

        findViewById(R.id.iv_back).setOnClickListener(this);

        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_next.setImageResource(R.drawable.done);
        iv_next.setOnClickListener(this);

        findViewById(R.id.btn_brightness).setOnClickListener(this);

        findViewById(R.id.btn_contrast).setOnClickListener(this);

        findViewById(R.id.btn_saturation).setOnClickListener(this);

        findViewById(R.id.btn_hue).setOnClickListener(this);

        findViewById(R.id.btn_sepia).setOnClickListener(this);

        findViewById(R.id.btn_sharpness).setOnClickListener(this);

        findViewById(R.id.btn_bulge_distortion).setOnClickListener(this);

        findViewById(R.id.btn_emboss).setOnClickListener(this);

        effectName = "Brightness";

        filter = new GPUImageBrightnessFilter();

        gv_image.setFilter(filter);

        mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(filter);

        /*findViewById(R.id.btn_cancel).setOnClickListener(this);

        findViewById(R.id.btn_apply).setOnClickListener(this);

        ll_doEffect = (LinearLayout) findViewById(R.id.ll_doEffect);*/

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back:

                finish();

                intent = new Intent(Edit_Images.this, Fragement_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_down_out, R.anim.slide_down_in);

                break;

            case R.id.iv_next:

                /*if (ll_doEffect.getVisibility() == View.VISIBLE) {

                    hsv_scrollEffcts.setVisibility(View.VISIBLE);

                    ll_doEffect.setVisibility(View.GONE);

                }*/

                GPUImage final_img = gv_image.getGPUImage();

                Bitmap bmp_final = null;

                bmp_final = final_img.getBitmapWithFilterApplied();

                if (bmp_final != null) {

                    Common.selected_bitmaps.set(Common.selected_image_number, bmp_final);

                }

                filter = null;

                bmp_original = null;

                intent = new Intent(Edit_Images.this, Fragement_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_down_out, R.anim.slide_down_in);

                break;

            case R.id.btn_brightness:

                iv_imgEffectIcon.setImageResource(R.drawable.brightness);

                filter = new GPUImageBrightnessFilter();

                applyGPUFilter(filter, "Brightness");

                break;

            case R.id.btn_contrast:

                iv_imgEffectIcon.setImageResource(R.drawable.contrast);

                filter = new GPUImageContrastFilter();

                applyGPUFilter(filter, "Contrast");

                break;

            case R.id.btn_saturation:

                iv_imgEffectIcon.setImageResource(R.drawable.saturation);

                filter = new GPUImageSaturationFilter();

                applyGPUFilter(filter, "Saturation");

                break;

            case R.id.btn_hue:

                iv_imgEffectIcon.setImageResource(R.drawable.hue);

                filter = new GPUImageHueFilter();

                applyGPUFilter(filter, "Hue");

                break;

            case R.id.btn_sepia:

                iv_imgEffectIcon.setImageResource(R.drawable.sepia);

                filter = new GPUImageSepiaFilter();

                applyGPUFilter(filter, "Sepia");

                break;

            case R.id.btn_sharpness:

                iv_imgEffectIcon.setImageResource(R.drawable.sharpness);

                filter = new GPUImageSharpenFilter();

                applyGPUFilter(filter, "Sharpness");

                break;

            case R.id.btn_bulge_distortion:

                iv_imgEffectIcon.setImageResource(R.drawable.bulge_destortion);

                filter = new GPUImageBulgeDistortionFilter();

                applyGPUFilter(filter, "Bulge Distortion");

                break;

            case R.id.btn_emboss:

                iv_imgEffectIcon.setImageResource(R.drawable.emboss);

                filter = new GPUImageEmbossFilter();

                applyGPUFilter(filter, "Emboss");

                break;

            /*case R.id.btn_cancel:

                //isApply = true;

                animateHSV = true;

                gv_image.requestRender();

                //sb_seekEffect.setProgress(0);

                tv_headerText.setText("Edit Image");

                ll_doEffect.startAnimation(ll_slideDown);

                hsv_scrollEffcts.startAnimation(zoomIn);

                //setProperLayout();

                *//*hsv_scrollEffcts.setVisibility(View.VISIBLE);

                ll_doEffect.setVisibility(View.GONE);*//*

                break;

            case R.id.btn_apply:

                //isApply = true;

                //sb_seekEffect.setProgress(0);

                animateHSV = true;

                ll_doEffect.startAnimation(ll_slideDown);

                hsv_scrollEffcts.startAnimation(zoomIn);

                GPUImage image = gv_image.getGPUImage();

                Bitmap bmp_filtered = image.getBitmapWithFilterApplied();

                Bitmap bmp = null;

                //gv_image.setImage(bmp);

                gv_image.setImage(bmp_filtered);

               *//* hsv_scrollEffcts.setVisibility(View.VISIBLE);

                zoomInOut = AnimationUtils.loadAnimation(Edit_Images.this, R.anim.zoom_in);
                hsv_scrollEffcts.startAnimation(zoomInOut);

                ll_doEffect.setVisibility(View.GONE);

                sb_seekEffect.setProgress(sb_progress);*//*

                break;*/

            default:

                break;

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (progress > 3) {

            if (mFilterAdjuster != null) {

                if (effectName.equals("Brightness")) {

                    if (progress <= 10) {

                        mFilterAdjuster.adjust((progress * 10) / 3);

                    } else {

                        if (progress > 13 && progress <= 45) {

                            mFilterAdjuster.adjust((int) (((progress * 4) / 2.5) + 12));

                        }
                    }
                } else if (effectName.equals("Contrast") || effectName.equals("Hue")) {

                    mFilterAdjuster.adjust(progress * 3);

                } else if (effectName.equals("Saturation")) {

                    mFilterAdjuster.adjust(progress * 5);

                } else if (effectName.equals("Sepia")) {

                    mFilterAdjuster.adjust(progress);

                } else if (effectName.equals("Sharpness") ||
                        effectName.equals("Bulge Distortion")) {

                    mFilterAdjuster.adjust(progress * 2);

                } else if (effectName.equals("Emboss")) {

                    mFilterAdjuster.adjust(progress);

                }
            }
        }

        gv_image.requestRender();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void applyGPUFilter(GPUImageFilter mFilter, String effectName) {

        sb_seekEffect.setProgress(0);

        this.effectName = effectName;

        tv_headerText.setText(effectName);

        tv_animEffect.setText(effectName);

        tv_animEffect.startAnimation(zoomIn);

        /*hsv_scrollEffcts.startAnimation(hsv_zoomOut);

        ll_doEffect.startAnimation(ll_slideUp);*/

        gv_image.setFilter(mFilter);

        mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(mFilter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        intent = new Intent(Edit_Images.this, Fragement_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_down_out, R.anim.slide_down_in);

    }

    @Override
    public void onAnimationStart(Animation animation) {

        if (animation == zoomIn) {

            //if (animateHSV == false) {

            tv_animEffect.setVisibility(View.VISIBLE);

           /* } else {

                animateHSV = false;

                hsv_scrollEffcts.setVisibility(View.VISIBLE);

            }
        } else if (animation == ll_slideUp) {

            ll_doEffect.setVisibility(View.VISIBLE);*/

        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        tv_animEffect.setVisibility(View.GONE);

        /*if (animation == hsv_zoomOut) {

            hsv_scrollEffcts.setVisibility(View.GONE);

        } else if (animation == ll_slideDown) {

            ll_doEffect.setVisibility(View.GONE);

        }*/
    }
}
