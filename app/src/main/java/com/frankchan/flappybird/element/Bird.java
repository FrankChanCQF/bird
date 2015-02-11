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

    private int sizeWidth,sizeHeight;

    public Bird(Context context, int width, int height, Bitmap bitmap) {
        super(context, width, height, bitmap);
        sizeWidth = Util.dp2px(getContext(), Constant.BIRD_SIZE_DIP);
        sizeHeight = sizeWidth*getBitmap().getHeight()/getBitmap().getWidth();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        marginLeft = getWidth()/2- sizeWidth /2;
        marginTop = getHeight()*Constant.BIRD_MARGIN_TOP;
    }

    @Override
    public void drawElement(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        RectF rectF = new RectF(marginLeft,marginTop,marginLeft+ sizeWidth,marginTop+ sizeHeight);
        canvas.drawBitmap(getBitmap(),null,rectF,null);
        canvas.restore();
    }

    @Override
    public void verticalMoveBy(int distance) {
        marginTop+=distance;
    }

    @Override
    public void horizontalMoveBy(int distance) {

    }
}
