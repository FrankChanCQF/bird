package com.frankchan.flappybird.element;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by frankchan on 2015/2/9.
 */
public class Background extends AbsElement{

    public Background(Context context, int width, int height, Bitmap bitmap) {
        super(context, width, height, bitmap);
    }

    @Override
    public void onCreate(Context context) {

    }

    @Override
    public void onDraw(Canvas canvas) {
        mRectF.set(0,0, getCanvasWidth(), getCanvasHeight());
        canvas.drawBitmap(getBitmap(),null,mRectF,null);
    }

    @Override
    public void onDestroy() {

    }
}
