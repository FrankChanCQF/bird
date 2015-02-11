package com.frankchan.flappybird.element;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by frankchan on 2015/2/9.
 */
public abstract class AbsElement {

    private int width;

    private int height;

    private Bitmap bitmap;

    private Context context;

    protected float marginLeft,marginTop;

    protected AbsElement(Context context,int width, int height, Bitmap bitmap) {
        this.bitmap = bitmap;
        this.context = context;
        setSize(width,height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Context getContext() {
        return context;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public abstract void drawElement(Canvas canvas);


    public interface Movable {

        //垂直移动
        void verticalMoveBy(int distance);

        //水平移动
        void horizontalMoveBy(int distance);

    }

}
