package com.example.androidcodes.photoeditingapp.PIP;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.MainActivity;
import com.example.androidcodes.photoeditingapp.PIP.Adapter.RecyclerView_Adapter;
import com.example.androidcodes.photoeditingapp.PIP.Common.PIP_Common;
import com.example.androidcodes.photoeditingapp.R;
import com.example.androidcodes.photoeditingapp.Save_Frame_Activity;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by peacock on 2/6/16.
 */
public class PIP_Effect_Activity extends AppCompatActivity implements View.OnClickListener,
        RecyclerView_Adapter.OnItemClickListener, View.OnTouchListener {

    private final int NONE = 0, DRAG = 1, ZOOM = 2;

    private int[] frames = {R.drawable.f_0, R.drawable.f_1, R.drawable.f_2, R.drawable.f_3,
            R.drawable.f_4, R.drawable.f_6, R.drawable.f_7, R.drawable.f_8, R.drawable.f_9,
            R.drawable.f_10, R.drawable.f_11, R.drawable.f_12};

    private int[] masks = {R.drawable.m_0, R.drawable.m_1, R.drawable.m_2, R.drawable.m_3,
            R.drawable.m_4, R.drawable.m_6, R.drawable.m_7, R.drawable.m_8, R.drawable.m_9,
            R.drawable.m_10, R.drawable.m_11, R.drawable.m_12};

    private int[] effectIcons = {R.drawable.icon_bannan, R.drawable.icon_autum,
            R.drawable.icon_grayscale, R.drawable.icon_hdr, R.drawable.icon_redsepia,
            R.drawable.icon_greenlight, R.drawable.icon_invert, R.drawable.icon_blackwhite,
            R.drawable.icon_classiclomo, R.drawable.icon_purplesepia, R.drawable.icon_tint};

    private int mode = NONE;

    private int lastFrameNo = 0, lastF_EffectNo = 0, lastB_EffectNo = 0;

    private int maskImg = masks[PIP_Common.selectedFrame],
            frameImg = frames[PIP_Common.selectedFrame];

    private float oldDist = 1f;

    private String[] effectName = {"Original", "Brightness", "Greyscale", "Blue", "Red Sepia",
            "Green Light", "Invert", "Black And White", "Vignette", "Purple Sepia", "Tint"};

    private Matrix matrix = new Matrix();

    private Matrix savedMatrix = new Matrix();

    private PointF start = new PointF();

    private PointF mid = new PointF();

    private Effects effects = new Effects();

    private Bitmap selectedImg = null, blurredImg = null, tempBlurredImg = null,
            tempSelectedImg = null;

    private Activity activity;

    private KProgressHUD pd;

    private ImageView iv_blurImg, iv_selectedImg, iv_maskImg, iv_frameImg;

    private RecyclerView rv_recyclerView;

    private TextView tv_frame, tv_foreground, tv_background;

    private RecyclerView_Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pip_effect_ativity);

        activity = PIP_Effect_Activity.this;

        selectedImg = tempSelectedImg = PIP_Common.PIP_bitmap;

        blurredImg = tempBlurredImg = effects.fastblur(selectedImg, 20);

        iv_blurImg = (ImageView) findViewById(R.id.iv_blurImg);

        iv_selectedImg = (ImageView) findViewById(R.id.iv_selectedImg);
        iv_selectedImg.setOnTouchListener(this);

        iv_maskImg = (ImageView) findViewById(R.id.iv_maskImg);

        iv_frameImg = (ImageView) findViewById(R.id.iv_frameImg);

        findViewById(R.id.iv_back).setOnClickListener(this);

        TextView tv_headerText = (TextView) findViewById(R.id.tv_headerText);
        tv_headerText.setText(getString(R.string.pip_text));

        findViewById(R.id.iv_next).setOnClickListener(this);

        tv_frame = (TextView) findViewById(R.id.tv_frame);
        tv_frame.setBackgroundResource(R.drawable.bg_tab);
        tv_frame.setOnClickListener(this);

        tv_foreground = (TextView) findViewById(R.id.tv_foreground);
        tv_foreground.setOnClickListener(this);

        tv_background = (TextView) findViewById(R.id.tv_background);
        tv_background.setOnClickListener(this);

        rv_recyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        rv_recyclerView.setLayoutManager(new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL, false));
        rv_recyclerView.setHasFixedSize(true);
        rv_recyclerView.dispatchNestedFling(100.0f, 100.0f, true);

        adapter = new RecyclerView_Adapter(activity, frames, null);
        rv_recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        pd = new KProgressHUD(activity).setCornerRadius(4.0f).
                setCancellable(false).setDimAmount(5.0f).
                setWindowColor(Color.parseColor("#85000000")).show();

        PIP_Common.catagory = "frames";

        new ApplyMask(tempBlurredImg, tempSelectedImg, maskImg, frameImg).
                executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        iv_selectedImg.setScaleType(ImageView.ScaleType.MATRIX);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back:

                PIP_Common.selectedFrame = 0;

                PIP_Common.selectedBackground = PIP_Common.selectedForeground = -1;

                Common.changeActivity(activity, MainActivity.class, true);

                break;

            case R.id.iv_next:

                Common.isFromPIP = true;

                FrameLayout fl_finalPIP = (FrameLayout) findViewById(R.id.fl_finalPIP);
                fl_finalPIP.setDrawingCacheEnabled(true);

                Common.final_frame = fl_finalPIP.getDrawingCache(true);

                startActivity(new Intent(activity, Save_Frame_Activity.class));

                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                break;

            case R.id.tv_frame:

                tabSetter("frames", tv_frame, tv_foreground, tv_background, frames, null,
                        PIP_Common.selectedFrame);

                /*PIP_Common.catagory = "frames";

                selector(tv_frame, tv_foreground, tv_background);

                adapter = new RecyclerView_Adapter(activity, frames, null);

                rv_recyclerView.setAdapter(adapter);

                rv_recyclerView.scrollToPosition(PIP_Common.selectedFrame);*/

                break;

            case R.id.tv_foreground:

                tabSetter("foreground", tv_foreground, tv_frame, tv_background, effectIcons,
                        effectName, PIP_Common.selectedForeground);

                /*PIP_Common.catagory = "foreground";

                selector(tv_foreground, tv_frame, tv_background);

                adapter = new RecyclerView_Adapter(activity, effectIcons, effectName);

                rv_recyclerView.setAdapter(adapter);

                rv_recyclerView.scrollToPosition(PIP_Common.selectedForeground);*/

                break;

            case R.id.tv_background:

                tabSetter("background", tv_background, tv_foreground, tv_frame, effectIcons,
                        effectName, PIP_Common.selectedBackground);

                /*PIP_Common.catagory = "background";

                selector(tv_background, tv_foreground, tv_frame);

                adapter = new RecyclerView_Adapter(activity, effectIcons, effectName);

                rv_recyclerView.setAdapter(adapter);

                rv_recyclerView.scrollToPosition(PIP_Common.selectedBackground);*/

                break;

            default:

                break;

        }

        adapter.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(View view, int position) {

        //Toast.makeText(PIP_Effect_Activity.this, "" + position, Toast.LENGTH_SHORT).show();

        adapter.setSelected(position);

        if (PIP_Common.catagory.equals("frames") && lastFrameNo != position) {

            lastFrameNo = position;

            frameImg = frames[position];

            maskImg = masks[position];

            new ApplyEffect(0, null).execute();

        } else if (PIP_Common.catagory.equals("foreground") && lastF_EffectNo != position) {

            lastF_EffectNo = position;

            new ApplyEffect(position, selectedImg).execute();

            //tempSelectedImg = applyEffect(position, selectedImg);

        } else if (PIP_Common.catagory.equals("background") && lastB_EffectNo != position) {

            lastB_EffectNo = position;

            new ApplyEffect(position, blurredImg).execute();

            //tempBlurredImg = applyEffect(position, blurredImg);

        }

        /*new ApplyMask(tempBlurredImg, tempSelectedImg, maskImg, frameImg).
                executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/

    }

    private void tabSetter(String category, TextView selected, TextView tv1, TextView tv2,
                           int[] icons, String[] names, int scrollToPosition) {

        PIP_Common.catagory = category;

        selected.setBackgroundResource(R.drawable.bg_tab);

        tv1.setBackgroundResource(0);

        tv2.setBackgroundResource(0);

        //selector(selected, tv1, tv2);

        adapter = new RecyclerView_Adapter(activity, icons, names);

        rv_recyclerView.setAdapter(adapter);

        rv_recyclerView.scrollToPosition(scrollToPosition);

    }

    private Bitmap applyEffect(int position, Bitmap bitmap) {

        Bitmap effectedBmp = null;

        switch (position) {

            case 0:

                /*if (PIP_Common.catagory.equals("foreground")) {

                    tempSelectedImg = selectedImg;

                } else {

                    tempBlurredImg = blurredImg;

                }*/

                return bitmap;

            case 1:

                return (effectedBmp = effects.doBrightness(bitmap, 50));

            case 2:

                return (effectedBmp = effects.doGreyscale(bitmap));

            case 3:

                return (effectedBmp = effects.doSepiaToning(bitmap, 45, 0.6, 0.6, 3.6));

            case 4:

                return (effectedBmp = effects.doSepiaToning(bitmap, 50, 1.6, 0.6, 0.6));

            case 5:

                return (effectedBmp = effects.doSepiaToning(bitmap, 30, 0.6, 1.6, 0.6));

            case 6:

                return (effectedBmp = effects.doInvert(bitmap));

            case 7:

                return (effectedBmp = effects.doBlackAndWhite(bitmap));

            case 8:

                return (effectedBmp = effects.doVignette(bitmap));

            case 9:

                return (effectedBmp = effects.doSepiaToning(bitmap, 35, 2.6, 0.6, 5.0));

            case 10:

                return (effectedBmp = effects.doTintImage(bitmap, 70));

            default:

                return bitmap;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView result_gallery = (ImageView) v;

        float scale;

        dumpEvent(event);

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:   // first finger down only

                savedMatrix.set(matrix);

                start.set(event.getX(), event.getY());

                mode = DRAG;

                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;

                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);

                if (oldDist > 5f) {

                    savedMatrix.set(matrix);

                    midPoint(mid, event);

                    mode = ZOOM;

                }

                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {

                    matrix.set(savedMatrix);

                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points

                } else if (mode == ZOOM) {
                    // pinch zooming
                    float newDist = spacing(event);
                    if (newDist > 5f) {

                        matrix.set(savedMatrix);

                        scale = newDist / oldDist; // setting the scaling of the

                        matrix.postScale(scale, scale, mid.x, mid.y);

                    }
                }

                break;

        }

        result_gallery.setImageMatrix(matrix); // display the transformation on screen

        return true;
    }

    private float spacing(MotionEvent event) {

        float x = event.getX(0) - event.getX(1);

        float y = event.getY(0) - event.getY(1);

        return (float) Math.sqrt(x * x + y * y);

    }

    private void midPoint(PointF point, MotionEvent event) {

        float x = event.getX(0) + event.getX(1);

        float y = event.getY(0) + event.getY(1);

        point.set(x / 2, y / 2);

    }

    private void dumpEvent(MotionEvent event) {

        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP",
                "7?", "8?", "9?"};

        StringBuilder sb = new StringBuilder();

        int action = event.getAction();

        int actionCode = action & MotionEvent.ACTION_MASK;

        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN ||
                actionCode == MotionEvent.ACTION_POINTER_UP) {

            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);

            sb.append(")");

        }

        sb.append("[");

        for (int i = 0; i < event.getPointerCount(); i++) {

            sb.append("#").append(i);

            sb.append("(pid ").append(event.getPointerId(i));

            sb.append(")=").append((int) event.getX(i));

            sb.append(",").append((int) event.getY(i));

            if (i + 1 < event.getPointerCount())
                sb.append(";");

        }

        sb.append("]");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        PIP_Common.selectedFrame = 0;

        PIP_Common.selectedBackground = PIP_Common.selectedForeground = -1;

        Common.changeActivity(activity, MainActivity.class, true);

    }

    /*private void selector(TextView selected, TextView tv1, TextView tv2) {

        selected.setBackgroundResource(R.drawable.bg_tab);

        tv1.setBackgroundResource(0);

        tv2.setBackgroundResource(0);

    }*/

    /*@Override
    protected void onDestroy() {
        super.onDestroy();

        if (Common.final_frame.isRecycled() == true) {

            Toast.makeText(PIP_Effect_Activity.this, "recycled", Toast.LENGTH_SHORT).show();

        }

        *//*if (selectedImg.isRecycled() == false) {

            selectedImg.recycle();

            selectedImg = null;

        }

        if (tempSelectedImg.isRecycled() == false) {

            tempSelectedImg.recycle();

            tempSelectedImg = null;

        }

        if (blurredImg.isRecycled() == false) {

            blurredImg.recycle();

            blurredImg = null;

        }

        if (tempBlurredImg.isRecycled() == false) {

            tempBlurredImg.recycle();

            tempBlurredImg = null;

        }*//*
    }*/

    private class ApplyEffect extends AsyncTask<Void, Void, Void> {

        int position = 0;

        Bitmap bitmap = null;

        public ApplyEffect(int position, Bitmap bitmap) {

            this.position = position;

            this.bitmap = bitmap;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (PIP_Common.catagory.equals("foreground") ||
                    PIP_Common.catagory.equals("background")) {

                pd.show();

            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (PIP_Common.catagory.equals("foreground")) {

                tempSelectedImg = applyEffect(position, bitmap);

            } else if (PIP_Common.catagory.equals("background")) {

                tempBlurredImg = applyEffect(position, bitmap);

            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new ApplyMask(tempBlurredImg, tempSelectedImg, maskImg, frameImg).
                    executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }
    }

    private class ApplyMask extends AsyncTask<Void, Void, Void> {

        private Bitmap selectedImg = null, blurredImg = null, maskBmp = null;

        private int frameImg = 0, maskImg = 0;

        public ApplyMask(Bitmap blurredImg, Bitmap selectedImg, int maskImg, int frameImg) {

            this.blurredImg = blurredImg;

            this.selectedImg = selectedImg;

            this.maskImg = maskImg;

            this.frameImg = frameImg;

        }

        @Override
        protected Void doInBackground(Void... params) {

            Bitmap mask = BitmapFactory.decodeResource(getResources(), maskImg);

            blurredImg = Bitmap.createScaledBitmap(blurredImg, mask.getWidth(),
                    mask.getHeight(), true);

            maskBmp = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(),
                    Bitmap.Config.ARGB_8888);

            Canvas mCanvas = new Canvas(maskBmp);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

            mCanvas.drawBitmap(blurredImg, 0, 0, null);
            mCanvas.drawBitmap(mask, 0, 0, paint);

            paint.setXfermode(null);

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    pd.dismiss();

                    iv_blurImg.setImageBitmap(blurredImg);

                    iv_maskImg.setImageBitmap(maskBmp);

                    iv_selectedImg.setImageBitmap(selectedImg);

                    iv_frameImg.setImageResource(frameImg);

                }
            });
        }
    }
}
