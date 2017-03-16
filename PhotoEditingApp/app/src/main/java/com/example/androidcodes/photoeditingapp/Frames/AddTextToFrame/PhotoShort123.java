package com.example.androidcodes.photoeditingapp.Frames.AddTextToFrame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.androidcodes.photoeditingapp.R;


public class PhotoShort123 extends RelativeLayout {
    public static ImageView image;
    public LayoutInflater mInflater;
    int baseh;
    int basew;
    int basex;
    int basey;
    ImageView btndel;
    ImageView btnrot;
    ImageView btnscl;
    RelativeLayout clip;
    Context cntx;
    boolean freeze = false;
    int h;
    int i;
    String imageUri;
    ImageView imgring;
    boolean isShadow;
    int iv;
    RelativeLayout layBg;
    RelativeLayout layGroup;
    LayoutParams layoutParams;
    int margl;
    int margt;
    float opacity = 1.0f;
    Bitmap originalBitmap;
    int pivx;
    int pivy;
    int pos;
    Bitmap shadowBitmap;
    float startDegree;
    String[] v;

    public PhotoShort123(Context paramContext) {
        super(paramContext);
        this.cntx = paramContext;
        this.layGroup = this;
        this.basex = 0;
        this.basey = 0;
        this.pivx = 0;
        this.pivy = 0;
        this.mInflater = (LayoutInflater) paramContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mInflater.inflate(R.layout.adjust_text, this, true);
        this.btndel = (ImageView) findViewById(R.id.del);
        this.btnrot = (ImageView) findViewById(R.id.rotate);
        this.btnscl = (ImageView) findViewById(R.id.sacle);
        this.imgring = (ImageView) findViewById(R.id.image);
        this.layoutParams = new LayoutParams(400, 400);
        this.layGroup.setLayoutParams(this.layoutParams);
        image = (ImageView) findViewById(R.id.clipart);
        image.setTag(Integer.valueOf(0));
        setOnTouchListener(new OnTouchListener() {
            final GestureDetector gestureDetector;

            {
                this.gestureDetector = new GestureDetector(PhotoShort123.this.cntx, new SimpleOnGestureListener() {
                    public boolean onDoubleTap(MotionEvent paramAnonymous2MotionEvent) {
                        return false;
                    }
                });
            }

            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                PhotoShort123.this.visiball();
                if (!PhotoShort123.this.freeze) {
                    switch (event.getAction()) {
                        case /*R.styleable.MapAttrs_mapType*/ 0:
                            PhotoShort123.this.layGroup.invalidate();
                            this.gestureDetector.onTouchEvent(event);
                            PhotoShort123.this.layGroup.bringToFront();
                            PhotoShort123.this.layGroup.performClick();
                            PhotoShort123.this.basex = (int) (event.getRawX() - ((float) PhotoShort123.this.layoutParams.leftMargin));
                            PhotoShort123.this.basey = (int) (event.getRawY() - ((float) PhotoShort123.this.layoutParams.topMargin));
                            break;
                        case /*ServiceHandler.POST*/ 2:
                            int i = (int) event.getRawX();
                            int j = (int) event.getRawY();
                            PhotoShort123.this.layBg = (RelativeLayout) PhotoShort123.this.getParent();
                            if (i - PhotoShort123.this.basex > (-((PhotoShort123.this.layGroup.getWidth() * 2) / 3)) && i - PhotoShort123.this.basex < PhotoShort123.this.layBg.getWidth() - (PhotoShort123.this.layGroup.getWidth() / 3)) {
                                PhotoShort123.this.layoutParams.leftMargin = i - PhotoShort123.this.basex;
                            }
                            if (j - PhotoShort123.this.basey > (-((PhotoShort123.this.layGroup.getHeight() * 2) / 3)) && j - PhotoShort123.this.basey < PhotoShort123.this.layBg.getHeight() - (PhotoShort123.this.layGroup.getHeight() / 3)) {
                                PhotoShort123.this.layoutParams.topMargin = j - PhotoShort123.this.basey;
                            }
                            PhotoShort123.this.layoutParams.rightMargin = -9999999;
                            PhotoShort123.this.layoutParams.bottomMargin = -9999999;
                            PhotoShort123.this.layGroup.setLayoutParams(PhotoShort123.this.layoutParams);
                            break;
                    }
                }
                return true;
            }
        });
        this.btnscl.setOnTouchListener(new OnTouchListener() {
            @SuppressLint({"NewApi"})
            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                if (PhotoShort123.this.freeze) {
                    return PhotoShort123.this.freeze;
                }
                int j = (int) event.getRawX();
                int i = (int) event.getRawY();
                PhotoShort123.this.layoutParams = (LayoutParams) PhotoShort123.this.layGroup.getLayoutParams();
                switch (event.getAction()) {
                    case /*R.styleable.MapAttrs_mapType*/ 0:
                        PhotoShort123.this.layGroup.invalidate();
                        PhotoShort123.this.basex = j;
                        PhotoShort123.this.basey = i;
                        PhotoShort123.this.basew = PhotoShort123.this.layGroup.getWidth();
                        PhotoShort123.this.baseh = PhotoShort123.this.layGroup.getHeight();
                        PhotoShort123.this.layGroup.getLocationOnScreen(new int[2]);
                        PhotoShort123.this.margl = PhotoShort123.this.layoutParams.leftMargin;
                        PhotoShort123.this.margt = PhotoShort123.this.layoutParams.topMargin;
                        break;
                    case /*ServiceHandler.POST*/ 2:
                        float f2 = (float) Math.toDegrees(Math.atan2((double) (i - PhotoShort123.this.basey), (double) (j - PhotoShort123.this.basex)));
                        float f1 = f2;
                        if (f2 < 0.0f) {
                            f1 = f2 + 360.0f;
                        }
                        j -= PhotoShort123.this.basex;
                        int k = i - PhotoShort123.this.basey;
                        i = (int) (Math.sqrt((double) ((j * j) + (k * k))) * Math.cos(Math.toRadians((double) (f1 - PhotoShort123.this.layGroup.getRotation()))));
                        j = (int) (Math.sqrt((double) ((i * i) + (k * k))) * Math.sin(Math.toRadians((double) (f1 - PhotoShort123.this.layGroup.getRotation()))));
                        k = (i * 2) + PhotoShort123.this.basew;
                        int m = (j * 2) + PhotoShort123.this.baseh;
                        if (k > 150) {
                            PhotoShort123.this.layoutParams.width = k;
                            PhotoShort123.this.layoutParams.leftMargin = PhotoShort123.this.margl - i;
                        }
                        if (m > 150) {
                            PhotoShort123.this.layoutParams.height = m;
                            PhotoShort123.this.layoutParams.topMargin = PhotoShort123.this.margt - j;
                        }
                        PhotoShort123.this.layGroup.setLayoutParams(PhotoShort123.this.layoutParams);
                        PhotoShort123.this.layGroup.performLongClick();
                        break;
                }
                return true;
            }
        });
        this.btnrot.setOnTouchListener(new OnTouchListener() {
            @SuppressLint({"NewApi"})
            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                if (PhotoShort123.this.freeze) {
                    return PhotoShort123.this.freeze;
                }
                PhotoShort123.this.layoutParams = (LayoutParams) PhotoShort123.this.layGroup.getLayoutParams();
                PhotoShort123.this.layBg = (RelativeLayout) PhotoShort123.this.getParent();
                int[] arrayOfInt = new int[2];
                PhotoShort123.this.layBg.getLocationOnScreen(arrayOfInt);
                int i = ((int) event.getRawX()) - arrayOfInt[0];
                int j = ((int) event.getRawY()) - arrayOfInt[1];
                switch (event.getAction()) {
                    case /*R.styleable.MapAttrs_mapType*/ 0:
                        PhotoShort123.this.layGroup.invalidate();
                        PhotoShort123.this.startDegree = PhotoShort123.this.layGroup.getRotation();
                        PhotoShort123.this.pivx = PhotoShort123.this.layoutParams.leftMargin + (PhotoShort123.this.getWidth() / 2);
                        PhotoShort123.this.pivy = PhotoShort123.this.layoutParams.topMargin + (PhotoShort123.this.getHeight() / 2);
                        PhotoShort123.this.basex = i - PhotoShort123.this.pivx;
                        PhotoShort123.this.basey = PhotoShort123.this.pivy - j;
                        break;
                    case /*ServiceHandler.POST*/ 2:
                        int k = PhotoShort123.this.pivx;
                        j = (int) (Math.toDegrees(Math.atan2((double) PhotoShort123.this.basey, (double) PhotoShort123.this.basex)) - Math.toDegrees(Math.atan2((double) (PhotoShort123.this.pivy - j), (double) (i - k))));
                        i = j;
                        if (j < 0) {
                            i = j + 360;
                        }
                        PhotoShort123.this.layGroup.setRotation((PhotoShort123.this.startDegree + ((float) i)) % 360.0f);
                        break;
                }/**/
                return true;
            }
        });
        this.btndel.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (!PhotoShort123.this.freeze) {
                    PhotoShort123.this.layBg = (RelativeLayout) PhotoShort123.this.getParent();
                    PhotoShort123.this.layBg.performClick();
                    PhotoShort123.this.layBg.removeView(PhotoShort123.this.layGroup);
                }
            }
        });
    }

    public void disableAll() {
        this.btndel.setVisibility(View.INVISIBLE);
        this.btnrot.setVisibility(View.INVISIBLE);
        this.btnscl.setVisibility(View.INVISIBLE);
        this.imgring.setVisibility(View.INVISIBLE);
    }

    public ImageView getImageView() {
        return image;
    }

    @SuppressLint({"NewApi"})
    public float getOpacity() {
        return image.getAlpha();
    }

    public void resetImage() {
        this.originalBitmap = null;
        this.layGroup.performLongClick();
    }

    public void setColor(int paramInt) {
        image.getDrawable().setColorFilter(null);
        image.getDrawable().setColorFilter(new ColorMatrixColorFilter(new float[]{0.33f, 0.33f, 0.33f, 0.0f, (float) Color.red(paramInt), 0.33f, 0.33f, 0.33f, 0.0f, (float) Color.green(paramInt), 0.33f, 0.33f, 0.33f, 0.0f, (float) Color.blue(paramInt), 0.0f, 0.0f, 0.0f, 1.0f, 0.0f}));
        image.setTag(Integer.valueOf(paramInt));
        this.layGroup.performLongClick();
    }

    public void setFreeze(boolean paramBoolean) {
        this.freeze = paramBoolean;
    }

    public void setImageId() {
        image.setId(this.layGroup.getId() + this.i);
        this.i++;
    }

    public void setLocation() {
        this.layBg = (RelativeLayout) getParent();
        LayoutParams localLayoutParams = (LayoutParams) this.layGroup.getLayoutParams();
        localLayoutParams.topMargin = (int) (Math.random() * ((double) (this.layBg.getHeight() - 400)));
        localLayoutParams.leftMargin = (int) (Math.random() * ((double) (this.layBg.getWidth() - 400)));
        this.layGroup.setLayoutParams(localLayoutParams);
    }

    public void visiball() {
        this.btndel.setVisibility(View.VISIBLE);
        this.btnrot.setVisibility(View.VISIBLE);
        this.btnscl.setVisibility(View.VISIBLE);
        this.imgring.setVisibility(View.VISIBLE);
    }

    public interface DoubleTapListener {
        void onDoubleTap();
    }
}
