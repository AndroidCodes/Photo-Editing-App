package com.example.androidcodes.photoeditingapp.Frames.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.Frames.CustomGallery.NewAdapters.SelectedImageAdapter;
import com.example.androidcodes.photoeditingapp.Frames.CustomGallery.New_CustomGallery;
import com.example.androidcodes.photoeditingapp.R;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

/**
 * Created by peacock on 26/3/16.
 */
public class Fragement_Activity extends FragmentActivity implements View.OnClickListener,
        ColorSeekBar.OnColorChangeListener {

    private Activity activity;

    private SelectedImageAdapter ra_adapter;

    private int[] frame_array_3 = {R.drawable.f31, R.drawable.f32, R.drawable.f33, R.drawable.f34,
            R.drawable.f35, R.drawable.f36, R.drawable.f37, R.drawable.f38, R.drawable.f39,
            R.drawable.f310};

    private int[] frame_array_4 = {R.drawable.f41, R.drawable.f42, R.drawable.f43, R.drawable.f44,
            R.drawable.f45, R.drawable.f46, R.drawable.f47, R.drawable.f48, R.drawable.f49,
            R.drawable.f410};

    private int[] frame_array_5 = {R.drawable.f51, R.drawable.f52, R.drawable.f53, R.drawable.f54,
            R.drawable.f55, R.drawable.f56, R.drawable.f57, R.drawable.f58, R.drawable.f59,
            R.drawable.f510};

    private ImageView iv_back, iv_next;

    private RecyclerView rv_frames;

    private TextView tv_changeFrameBackground, tv_changeFrames;

    private ColorSeekBar cps_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_activity);

        activity = Fragement_Activity.this;

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_next.setImageResource(R.drawable.done);
        iv_next.setOnClickListener(this);

        tv_changeFrameBackground = (TextView) findViewById(R.id.tv_changeFrameBackground);
        tv_changeFrameBackground.setOnClickListener(this);

        tv_changeFrames = (TextView) findViewById(R.id.tv_changeFrames);
        tv_changeFrames.setBackgroundResource(R.drawable.bg_tab);
        tv_changeFrames.setOnClickListener(this);

        rv_frames = (RecyclerView) findViewById(R.id.rv_frames);
        rv_frames.setLayoutManager(new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL, false));
        rv_frames.setItemAnimator(new DefaultItemAnimator());
        rv_frames.setHasFixedSize(true);

        cps_picker = (ColorSeekBar) findViewById(R.id.cps_picker);
        cps_picker.setOnColorChangeListener(this);
        /*cps_picker.setShowAlphaBar(true);
        cps_picker.setThumbHeight(25.0f);
        cps_picker.setBarHeight(7.0f);
        cps_picker.setMaxValue(100);
        cps_picker.setColorBarValue(50);*/

        if (Common.num_of_selected_images == 3) {

            ra_adapter = new SelectedImageAdapter(activity, null, frame_array_3, null);

        } else if (Common.num_of_selected_images == 4) {

            ra_adapter = new SelectedImageAdapter(activity, null, frame_array_4, null);

        } else if (Common.num_of_selected_images == 5) {

            ra_adapter = new SelectedImageAdapter(activity, null, frame_array_5, null);

        }

        ra_adapter.setOnItemClickListener(new SelectedImageAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(View view, int position) {

                if (Common.frame_number != position) {

                    Common.frame_number = position;

                    changeFragment();

                }
            }
        });

        rv_frames.setAdapter(ra_adapter);

        changeFragment();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_next:

                Common.isFromPIP = false;

                Intent visibility_intent = new Intent("Visibility");
                visibility_intent.putExtra("Visibility", "gone");
                LocalBroadcastManager.getInstance(activity).
                        sendBroadcast(visibility_intent);

                break;

            case R.id.iv_back:

                Common.frameColor = Color.WHITE;

                Common.changeActivity(activity, New_CustomGallery.class, true);

                /*finish();

                Intent intent = new Intent(this, CustomGalleryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                overridePendingTransition(R.anim.left_in, R.anim.right_out);*/

                break;

            case R.id.tv_changeFrames:

                tv_changeFrames.setBackgroundResource(R.drawable.bg_tab);

                tv_changeFrameBackground.setBackgroundResource(0);

                cps_picker.setVisibility(View.GONE);

                rv_frames.setVisibility(View.VISIBLE);

                break;

            case R.id.tv_changeFrameBackground:

                tv_changeFrames.setBackgroundResource(0);

                tv_changeFrameBackground.setBackgroundResource(R.drawable.bg_tab);

                rv_frames.setVisibility(View.GONE);

                cps_picker.setVisibility(View.VISIBLE);

                break;

            default:

                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        changeFragment();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Common.frameColor = Color.WHITE;

        Common.changeActivity(activity, New_CustomGallery.class, true);

        /*Intent intent = new Intent(activity, CustomGalleryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();

        overridePendingTransition(R.anim.left_in, R.anim.right_out);*/

    }

    private void changeFragment() {

        if (Common.num_of_selected_images == 3) {

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fl_container, new Frames_Three(), "frame_3").commit();

        } else if (Common.num_of_selected_images == 4) {

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fl_container, new Frames_Four(), "frame_4").commit();

        } else if (Common.num_of_selected_images == 5) {

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fl_container, new Frames_Five(), "frame_5").commit();

        }
    }

    @Override
    public void onColorChangeListener(int i1, int alphaBarValue, int colorBarValue) {

        Common.frameColor = colorBarValue;

        Common.changeFrameColor(activity);

        /*Common.iv_frame_1.getDrawable().setColorFilter(colorBarValue, PorterDuff.Mode.MULTIPLY);

        Common.iv_frame_2.getDrawable().setColorFilter(colorBarValue, PorterDuff.Mode.MULTIPLY);

        Common.iv_frame_3.getDrawable().setColorFilter(colorBarValue, PorterDuff.Mode.MULTIPLY);

        if (Common.threeFlag == true) {

            Common.iv_1.setBackgroundColor(colorBarValue);
            //getDrawable().setColorFilter(colorBarValue, PorterDuff.Mode.MULTIPLY);

            Common.iv_2.setBackgroundColor(colorBarValue);
            //getDrawable().setColorFilter(colorBarValue, PorterDuff.Mode.MULTIPLY);

            Common.iv_3.setBackgroundColor(colorBarValue);
            //getDrawable().setColorFilter(colorBarValue, PorterDuff.Mode.MULTIPLY);

            if (Common.fourFlag == true) {

                Common.iv_4.setBackgroundColor(colorBarValue);
                //getDrawable().setColorFilter(colorBarValue, PorterDuff.Mode.MULTIPLY);

            }
        }*/
    }
}

