package com.frankchan.flappybird.element;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.frankchan.flappybird.Constant;
import com.frankchan.flappybird.Util.Util;

/**
 * Created by frankchan on 2015/2/9.
 */
public class Bird extends AbsElement{

    private int sizeWidth,sizeHeight,raiseHeight,dropSpeed;

    public Bird(Context context, int width, int height, Bitmap bitmap) {
        super(context, width, height, bitmap);
        reset();
    }

    @Override
    public void onCreate(Context context) {
        sizeWidth = Util.dp2px(context, Constant.BIRD_SIZE_DIP);
        sizeHeight = sizeWidth*getHeight()/getWidth();
        raiseHeight = -Util.dp2px(context,Constant.BIRD_TOUCH_HEIGHT_DIP);
        dropSpeed = Util.dp2px(context,Constant.BIRD_DROP_SPEED_DIP);
        left = getCanvasWidth()/2- sizeWidth /2;
        top = getCanvasHeight()*Constant.BIRD_MARGIN_TOP;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        mRectF.set(left, top, left + sizeWidth, top + sizeHeight);
        if(enableRotation()){
            canvas.rotate(-getRotation(),getPivotX(),getPivotY());
        }
        canvas.drawBitmap(getBitmap(), null, mRectF, null);
        canvas.restore();
    }

    @Override
    public void onDestroy() {

    }

    public void reset(){
        setRotation(0);
    }

    public void raiseHead(){
        int rotation =getRotation()+Constant.BIRD_RAISE_ROTATION;
        setRotation(Math.min(Constant.BIRD_MAX_ROTATION,rotation));
    }

    public void bowHead(){
        int rotation =getRotation()-Constant.BIRD_LOWER_ROTATION;
        setRotation(Math.max(-Constant.BIRD_MAX_ROTATION,rotation));
    }

    @Override
    public void verticalMoveBy(int distance) {
        super.verticalMoveBy(distance);
        top = Math.max(0, top);
    }

    public void raiseByTouch(){
        verticalMoveBy(raiseHeight);
        raiseHead();
    }

    public void autoDrop(){
        verticalMoveBy(dropSpeed);
        bowHead();
    }

    @Override
    public float getLeft() {
        return super.getLeft();
    }

    @Override
    public float getRight() {
        return getLeft()+sizeWidth;
    }

    @Override
    public float getBottom() {
        return getTop()+sizeHeight;
    }

    @Override
    protected boolean enableRotation() {
        return true;
    }
}
