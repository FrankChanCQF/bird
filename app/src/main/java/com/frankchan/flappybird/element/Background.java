package com.frankchan.flappybird.element;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by frankchan on 2015/2/9.
 */
public class Background extends AbsElement{

    public Background(Context context, int width, int height, Bitmap bitmap) {
        super(context, width, height, bitmap);
    }

    @Override
    public void drawElement(Canvas canvas) {
        canvas.drawBitmap(getBitmap(),null,new RectF(0,0,getWidth(),getHeight()),null);
    }
}
