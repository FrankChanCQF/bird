package com.frankchan.flappybird.element;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.frankchan.flappybird.Constant;

/**
 * Created by frankchan on 2015/2/9.
 */
public class Floor extends AbsElement{

    private Paint mInnerPaint;

    public Floor(Context context, int width, int height, Bitmap bitmap) {
        super(context, width, height, bitmap);
    }

    @Override
    public void onCreate(Context context) {
        top = (int)(getCanvasHeight()* Constant.FLOOR_MARGIN_TOP);
        mInnerPaint = new Paint();
        mInnerPaint.setDither(true);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setShader(new BitmapShader(getBitmap(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }


    @Override
    public void onDraw(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        mRectF.set(left, top, getCanvasWidth(), getCanvasHeight());
        canvas.drawRect(mRectF,mInnerPaint);
        canvas.restore();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void horizontalMoveBy(int distance) {
        super.horizontalMoveBy(distance);
        if(-getLeft() >getWidth()){
            left = getLeft() % getWidth();
        }
    }

    @Override
    public boolean isCrash(AbsElement element) {
        if(element.getBottom()>getTop()){
            return true;
        }
        return super.isCrash(element);
    }

    @Override
    protected float getRight() {
        return getLeft()+ getCanvasWidth();
    }

    @Override
    protected float getBottom() {
        return getLeft()+ getCanvasHeight();
    }
}
