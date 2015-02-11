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
public class Floor extends AbsElement implements AbsElement.Movable,AbsElement.Crashable{

    private int blockWidth;

    private Paint mInnerPaint;

    public Floor(Context context, int width, int height, Bitmap bitmap) {
        super(context, width, height, bitmap);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        top = (int)(getHeight()* Constant.FLOOR_MARGIN_TOP);
        blockWidth = getBitmap().getWidth();
        mInnerPaint = new Paint();
        mInnerPaint.setDither(true);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setShader(new BitmapShader(getBitmap(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }

    @Override
    public void verticalMoveBy(int distance) {

    }

    @Override
    public void horizontalMoveBy(int distance) {
        left +=distance;
        if(-left >blockWidth){
            left = left %blockWidth;
        }
    }

    @Override
    public void drawElement(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        RectF rectF = new RectF(left, top,getWidth(),getHeight());
        canvas.drawRect(rectF,mInnerPaint);
        canvas.restore();
    }

    @Override
    public boolean isCrash(AbsElement element) {
        if(element.getBottom()>getTop()){
            return true;
        }
        return false;
    }
}
