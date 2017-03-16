package com.example.androidcodes.photoeditingapp.Frames.AddTextToFrame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PhotoSortrView123 extends View implements MultiTouchController.MultiTouchObjectCanvas {
    private static final float SCREEN_MARGIN = 100.0f;
    private static final int UI_MODE_ANISOTROPIC_SCALE = 2;
    private static final int UI_MODE_ROTATE = 1;
    Context cntx;
    private MultiTouchController.PointInfo currTouchPoint;
    private int displayHeight;
    private int displayWidth;
    private ArrayList mImages;
    private int mUIMode;
    private MultiTouchController multiTouchController;

    public PhotoSortrView123(Context context) {
        this(context, null);
    }

    public PhotoSortrView123(Context context, AttributeSet attributeset) {
        this(context, attributeset, 0);
    }

    public PhotoSortrView123(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        this.mImages = new ArrayList();
        this.multiTouchController = new MultiTouchController(this);
        this.currTouchPoint = new MultiTouchController.PointInfo();
        this.mUIMode = UI_MODE_ROTATE;
        init(context);
    }

    private void init(Context context) {
        int i;
        int j;
        this.cntx = context;
        Resources resources = context.getResources();
        setBackgroundColor(0);
        DisplayMetrics displaymetrics = resources.getDisplayMetrics();
        if (resources.getConfiguration().orientation == UI_MODE_ANISOTROPIC_SCALE) {
            i = Math.max(displaymetrics.widthPixels, displaymetrics.heightPixels);
        } else {
            i = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        }
        this.displayWidth = i;
        if (resources.getConfiguration().orientation == UI_MODE_ANISOTROPIC_SCALE) {
            j = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        } else {
            j = Math.max(displaymetrics.widthPixels, displaymetrics.heightPixels);
        }
        this.displayHeight = j;
    }

    public MultiTouchEntity getDraggableObjectAtPoint(MultiTouchController.PointInfo pt) {
        float x = pt.getX();
        float y = pt.getY();
        for (int i = this.mImages.size() - 1; i >= 0; i--) {
            ImageEntity123 im = (ImageEntity123) this.mImages.get(i);
            if (im.containsPoint(x, y)) {
                return im;
            }
        }
        return null;
    }

    public void getPositionAndScale(Object obj, MultiTouchController.PositionAndScale positionandscale) {
        getPositionAndScale((MultiTouchEntity) obj, positionandscale);
    }

    public void getPositionAndScale(MultiTouchEntity multitouchentity, MultiTouchController.PositionAndScale positionandscale) {
        boolean flag;
        boolean flag1;
        float f = multitouchentity.getCenterX();
        float f1 = multitouchentity.getCenterY();
        if ((this.mUIMode & UI_MODE_ANISOTROPIC_SCALE) == 0) {
            flag = true;
        } else {
            flag = false;
        }
        float f2 = (multitouchentity.getScaleX() + multitouchentity.getScaleY()) / 2.0f;
        if ((this.mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0) {
            flag1 = true;
        } else {
            flag1 = false;
        }
        float f3 = multitouchentity.getScaleX();
        float f4 = multitouchentity.getScaleY();
        boolean flag2 = false;
        if ((this.mUIMode & UI_MODE_ROTATE) != 0) {
            flag2 = true;
        }
        positionandscale.set(f, f1, flag, f2, flag1, f3, f4, flag2, multitouchentity.getAngle());
    }

    public void loadImages1(Context context, int i) {
        this.mImages.add(new ImageEntity123(i, context.getResources()));
        ((MultiTouchEntity) this.mImages.get(this.mImages.size() - 1)).load(context, SCREEN_MARGIN + ((float) (Math.random() * ((double) (((float) this.displayWidth) - 200.0f)))), SCREEN_MARGIN + ((float) (Math.random() * ((double) (((float) this.displayHeight) - 200.0f)))));
        invalidate();
    }

    public void loadText(Context context, Drawable drawable) {
        this.mImages.add(new ImageEntity123(drawable, context.getResources()));
        ((MultiTouchEntity) this.mImages.get(this.mImages.size() - 1)).load(context, SCREEN_MARGIN + ((float) (Math.random() * ((double) (((float) this.displayWidth) - 200.0f)))), SCREEN_MARGIN + ((float) (Math.random() * ((double) (((float) this.displayHeight) - 200.0f)))));
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = this.mImages.size();
        for (int j = 0; j < i; j += UI_MODE_ROTATE) {
            ((MultiTouchEntity) this.mImages.get(j)).draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent motionevent) {
        return this.multiTouchController.onTouchEvent(motionevent);
    }

    public boolean pointInObjectGrabArea(MultiTouchController.PointInfo pointinfo, Object obj) {
        return pointInObjectGrabArea(pointinfo, (MultiTouchEntity) obj);
    }

    public boolean pointInObjectGrabArea(MultiTouchController.PointInfo pointInfo, MultiTouchEntity multitouchentity) {
        return false;
    }

    public void removeImages1() {
        try {
            this.mImages.remove(this.mImages.size() - 1);
            invalidate();
        } catch (Exception e) {
        }
    }

    public void selectObject(Object obj, MultiTouchController.PointInfo pointinfo) {
        selectObject((MultiTouchEntity) obj, pointinfo);
    }

    public void selectObject(MultiTouchEntity multitouchentity, MultiTouchController.PointInfo pointinfo) {
        this.currTouchPoint.set(pointinfo);
        if (multitouchentity != null) {
            this.mImages.remove(multitouchentity);
            this.mImages.add(multitouchentity);
        }
        invalidate();
    }

    public boolean setPositionAndScale(Object obj, MultiTouchController.PositionAndScale positionandscale, MultiTouchController.PointInfo pointinfo) {
        return setPositionAndScale((MultiTouchEntity) obj, positionandscale, pointinfo);
    }

    public boolean setPositionAndScale(MultiTouchEntity multitouchentity, MultiTouchController.PositionAndScale positionandscale, MultiTouchController.PointInfo pointinfo) {
        this.currTouchPoint.set(pointinfo);
        boolean flag = ((ImageEntity123) multitouchentity).setPos(positionandscale);
        if (flag) {
            invalidate();
        }
        return flag;
    }

    public void trackballClicked() {
        this.mUIMode = (this.mUIMode + UI_MODE_ROTATE) % 3;
        invalidate();
    }
}
