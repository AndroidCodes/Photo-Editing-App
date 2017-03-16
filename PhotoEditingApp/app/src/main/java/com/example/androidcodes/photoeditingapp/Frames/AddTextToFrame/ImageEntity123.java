package com.example.androidcodes.photoeditingapp.Frames.AddTextToFrame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class ImageEntity123 extends MultiTouchEntity {
    private static final double INITIAL_SCALE_FACTOR = 0.15d;
    int chk;
    private Drawable ddd;
    private transient Drawable mDrawable;
    private int mResourceId;

    public ImageEntity123(int i, Resources resources) {
        super(resources);
        this.chk = 1;
        this.mResourceId = i;
    }

    public ImageEntity123(Drawable drawable, Resources resources) {
        super(resources);
        this.chk = 2;
        this.ddd = drawable;
    }

    public ImageEntity123(ImageEntity123 imageentity, Resources resources) {
        super(resources);
        this.mDrawable = imageentity.mDrawable;
        this.mResourceId = imageentity.mResourceId;
        this.ddd = imageentity.ddd;
        this.mScaleX = imageentity.mScaleX;
        this.mScaleY = imageentity.mScaleY;
        this.mCenterX = imageentity.mCenterX;
        this.mCenterY = imageentity.mCenterY;
        this.mAngle = imageentity.mAngle;
    }

    public void draw(Canvas canvas) {
        canvas.save();
        float f = (this.mMaxX + this.mMinX) / 2.0f;
        float f1 = (this.mMaxY + this.mMinY) / 2.0f;
        this.mDrawable.setBounds((int) this.mMinX, (int) this.mMinY, (int) this.mMaxX, (int) this.mMaxY);
        canvas.translate(f, f1);
        canvas.rotate((180.0f * this.mAngle) / 3.141593f);
        canvas.translate(-f, -f1);
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    public void load(Context context, float f, float f1) {
        float f2;
        float f3;
        float f5;
        float f4;
        Resources resources = context.getResources();
        getMetrics(resources);
        this.mStartMidX = f;
        this.mStartMidY = f1;
        this.mDrawable = this.ddd;
        if (this.chk == 1) {
            this.mDrawable = resources.getDrawable(this.mResourceId);
        }
        this.mWidth = this.mDrawable.getIntrinsicWidth();
        this.mHeight = this.mDrawable.getIntrinsicHeight();
        if (this.mFirstLoad) {
            f2 = f;
            f3 = f1;
            float f6 = (float) (INITIAL_SCALE_FACTOR * ((double) (((float) Math.max(this.mDisplayWidth, this.mDisplayHeight)) / ((float) Math.max(this.mWidth, this.mHeight)))));
            f5 = f6;
            f4 = f6;
            this.mFirstLoad = false;
        } else {
            f2 = this.mCenterX;
            f3 = this.mCenterY;
            f4 = this.mScaleX;
            f5 = this.mScaleY;
            float f7 = this.mAngle;
        }
        setPos(f2, f3, f4, f5, this.mAngle);
    }

    public void unload() {
        this.mDrawable = null;
    }
}
