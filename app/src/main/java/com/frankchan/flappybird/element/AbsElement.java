package com.frankchan.flappybird.element;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by frankchan on 2015/2/9.
 */
public abstract class AbsElement implements AbsLifeCycle{

    private int mCanvasWidth;

    private int mCanvasHeight;

    private Bitmap mBitmap;

    private Context mContext;

    //the top and left of element
    protected float left, top;

    protected RectF mRectF = new RectF();

    public AbsElement(Context context,int width, int height, Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.mContext = context;
        setCanvasSize(width, height);
        onCreate(context);
    }

    public int getCanvasWidth() {
        return mCanvasWidth;
    }

    public int getCanvasHeight() {
        return mCanvasHeight;
    }

    public Context getContext() {
        return mContext;
    }

    public void setCanvasSize(int width, int height){
        this.mCanvasWidth = width;
        this.mCanvasHeight = height;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void drawElement(Canvas canvas){
        if (mBitmap ==null||canvas==null)
            return;
        onDraw(canvas);
    };

    protected float getLeft() {
        return left;
    }

    protected float getTop() {
        return top;
    }

    protected float getRight() {
    return getLeft()+ getWidth();
}

    protected float getBottom() {
        return getTop()+ getHeight();
    }

    public int getWidth(){
        return mBitmap == null ? 0 : mBitmap.getWidth();
    }

    public int getHeight(){
        return  mBitmap == null ? 0 : mBitmap.getWidth();
    }

    protected boolean isCrash(AbsElement element) {
        return false;
    }

    public void verticalMoveBy(int distance) {
        top+=distance;
    }

    public void horizontalMoveBy(int distance) {
        left+=distance;
    }

    public static void recycleBitmap(Bitmap bitmap){
        if(bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
        }
    }

    public void recycle(){
        recycleBitmap(mBitmap);
        onDestroy();
    }
}
