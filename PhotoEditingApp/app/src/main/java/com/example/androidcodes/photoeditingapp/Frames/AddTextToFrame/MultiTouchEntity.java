package com.example.androidcodes.photoeditingapp.Frames.AddTextToFrame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.io.Serializable;

public abstract class MultiTouchEntity implements Serializable {
    protected static final int GRAB_AREA_SIZE = 40;
    private static final int UI_MODE_ANISOTROPIC_SCALE = 2;
    private static final int UI_MODE_ROTATE = 1;
    protected float mAngle;
    protected float mCenterX;
    protected float mCenterY;
    protected int mDisplayHeight;
    protected int mDisplayWidth;
    protected boolean mFirstLoad = true;
    protected float mGrabAreaX1;
    protected float mGrabAreaX2;
    protected float mGrabAreaY1;
    protected float mGrabAreaY2;
    protected int mHeight;
    protected boolean mIsGrabAreaSelected = false;
    protected boolean mIsLatestSelected = false;
    protected float mMaxX;
    protected float mMaxY;
    protected float mMinX;
    protected float mMinY;
    protected transient Paint mPaint = new Paint();
    protected float mScaleX;
    protected float mScaleY;
    protected float mStartMidX;
    protected float mStartMidY;
    protected int mUIMode = UI_MODE_ROTATE;
    protected int mWidth;

    public MultiTouchEntity(Resources resources) {
        getMetrics(resources);
    }

    public abstract void draw(Canvas canvas);

    public abstract void load(Context context, float f, float f2);

    public abstract void unload();

    public boolean containsPoint(float f, float f1) {
        return f >= this.mMinX && f <= this.mMaxX && f1 >= this.mMinY && f1 <= this.mMaxY;
    }

    public float getAngle() {
        return this.mAngle;
    }

    public float getCenterX() {
        return this.mCenterX;
    }

    public float getCenterY() {
        return this.mCenterY;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public float getMaxX() {
        return this.mMaxX;
    }

    public float getMaxY() {
        return this.mMaxY;
    }

    protected void getMetrics(Resources resources) {
        int i;
        int j;
        DisplayMetrics displaymetrics = resources.getDisplayMetrics();
        if (resources.getConfiguration().orientation == UI_MODE_ANISOTROPIC_SCALE) {
            i = Math.max(displaymetrics.widthPixels, displaymetrics.heightPixels);
        } else {
            i = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        }
        this.mDisplayWidth = i;
        if (resources.getConfiguration().orientation == UI_MODE_ANISOTROPIC_SCALE) {
            j = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        } else {
            j = Math.max(displaymetrics.widthPixels, displaymetrics.heightPixels);
        }
        this.mDisplayHeight = j;
    }

    public float getMinX() {
        return this.mMinX;
    }

    public float getMinY() {
        return this.mMinY;
    }

    public float getScaleX() {
        return this.mScaleX;
    }

    public float getScaleY() {
        return this.mScaleY;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public boolean grabAreaContainsPoint(float f, float f1) {
        return f >= this.mGrabAreaX1 && f <= this.mGrabAreaX2 && f1 >= this.mGrabAreaY1 && f1 <= this.mGrabAreaY2;
    }

    public boolean isGrabAreaSelected() {
        return this.mIsGrabAreaSelected;
    }

    public void reload(Context context) {
        this.mFirstLoad = false;
        load(context, this.mCenterX, this.mCenterY);
    }

    public void setIsGrabAreaSelected(boolean flag) {
        this.mIsGrabAreaSelected = flag;
    }

    protected boolean setPos(float f, float f1, float f2, float f3, float f4) {
        float f5 = f2 * ((float) (this.mWidth / UI_MODE_ANISOTROPIC_SCALE));
        float f6 = f3 * ((float) (this.mHeight / UI_MODE_ANISOTROPIC_SCALE));
        this.mMinX = f - f5;
        this.mMinY = f1 - f6;
        this.mMaxX = f + f5;
        this.mMaxY = f1 + f6;
        this.mGrabAreaX1 = this.mMaxX - 40.0f;
        this.mGrabAreaY1 = this.mMaxY - 40.0f;
        this.mGrabAreaX2 = this.mMaxX;
        this.mGrabAreaY2 = this.mMaxY;
        this.mCenterX = f;
        this.mCenterY = f1;
        this.mScaleX = f2;
        this.mScaleY = f3;
        this.mAngle = f4;
        return true;
    }

    public boolean setPos(MultiTouchController.PositionAndScale positionandscale) {
        float f;
        float f1;
        if ((this.mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0) {
            f = positionandscale.getScaleX();
        } else {
            f = positionandscale.getScale();
        }
        if ((this.mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0) {
            f1 = positionandscale.getScaleY();
        } else {
            f1 = positionandscale.getScale();
        }
        return setPos(positionandscale.getXOff(), positionandscale.getYOff(), f, f1, positionandscale.getAngle());
    }
}
