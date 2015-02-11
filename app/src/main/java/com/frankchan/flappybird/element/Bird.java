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
public class Bird extends AbsElement implements AbsElement.Movable{

    private int sizeWidth,sizeHeight,raiseHeight,dropSpeed;

    public Bird(Context context, int width, int height, Bitmap bitmap) {
        super(context, width, height, bitmap);
        sizeWidth = Util.dp2px(getContext(), Constant.BIRD_SIZE_DIP);
        sizeHeight = sizeWidth*getBitmap().getHeight()/getBitmap().getWidth();
        raiseHeight = -Util.dp2px(getContext(),Constant.BIRD_TOUCH_HEIGHT_DIP);
        dropSpeed = Util.dp2px(getContext(),Constant.BIRD_DROP_SPEED_DIP);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        left = getWidth()/2- sizeWidth /2;
        top = getHeight()*Constant.BIRD_MARGIN_TOP;
    }

    @Override
    public void drawElement(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        RectF rectF = new RectF(left, top, left + sizeWidth, top + sizeHeight);
        canvas.drawBitmap(getBitmap(),null,rectF,null);
        canvas.restore();
    }

    @Override
    public void verticalMoveBy(int distance) {
        top +=distance;
        top = Math.max(0, top);
    }

    @Override
    public void horizontalMoveBy(int distance) {

    }

    public void raiseByTouch(){
        verticalMoveBy(raiseHeight);
    }

    public void autoDrop(){
        verticalMoveBy(dropSpeed);
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
}
