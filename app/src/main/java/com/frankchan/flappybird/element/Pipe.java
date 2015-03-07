package com.frankchan.flappybird.element;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.frankchan.flappybird.Constant;
import com.frankchan.flappybird.Util.Util;

import java.util.Random;

/**
 * Created by frankchan on 2015/2/9.
 */
public class Pipe extends AbsElement{

    private Bitmap mExtraBitmap;

    private int pipeWidth;

    private int pipeMargin;

    private int pipeSpace;

    //上下管道长度
    private int[] pipeLengths;

    private boolean isAdded = false;

    private RectF mRectTop,mRectBottom;

    private static final Random random = new Random();

    private Pipe(Context context, int width, int height, Bitmap bitmap) {
        super(context, width, height, bitmap);
        mRectTop = new RectF();
        mRectBottom = new RectF();
    }

    public Pipe(Context context, int width, int height, Bitmap bitmap,Bitmap extraBitmap) {
        this(context, width, height, bitmap);
        this.mExtraBitmap = extraBitmap;
    }

    public int[] getPipeLength(){
        int[] pipes = new int[2];
        pipeMargin = (int)(getCanvasHeight()*Constant.PIPE_VERTICAL_MARGIN);
        pipeSpace = (int)(getCanvasHeight()*Constant.PIPE_SPACE);
        pipes[0] = random.nextInt((int)(getCanvasHeight()*(Constant.PIPE_MAX_HEIGHT-Constant.PIPE_MIN_HEIGHT)))+(int)(getCanvasHeight()*Constant.PIPE_MIN_HEIGHT);
        pipes[1] = pipeSpace-pipeMargin-pipes[0];
        return pipes;
    }


    @Override
    public void onCreate(Context context) {
        //管道默认从右边出现
        left = getCanvasWidth();
        pipeLengths = getPipeLength();
        pipeWidth = Util.dp2px(getContext(), Constant.PIPE_WIDTH_DIP);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(left,0);
        mRectTop.set(0,0,pipeWidth,pipeLengths[0]);
        canvas.drawBitmap(getBitmap(),null,mRectTop,null);
        canvas.translate(0, pipeLengths[0] + pipeMargin);
        mRectBottom.set(0,0,pipeWidth,pipeLengths[1]);
        canvas.drawBitmap(mExtraBitmap,null,mRectBottom,null);
        canvas.restore();
    }

    @Override
    public void onDestroy() {
        recycleBitmap(mExtraBitmap);
    }

    public boolean isOutScreen(){
        return left +pipeWidth<=0;
    }

    @Override
    public float getRight() {
        return getLeft()+pipeWidth;
    }

    @Override
    public boolean isCrash(AbsElement element) {
        if(element.getLeft()>=getLeft()&&element.getLeft()<=getRight()){
            if(element.getTop()<=pipeLengths[0]||element.getBottom()>=pipeLengths[0]+pipeMargin){
                return true;
            }
        }else if(element.getRight()>=getLeft()&&element.getRight()<=getRight()){
            if(element.getTop()<=pipeLengths[0]||element.getBottom()>=pipeLengths[0]+pipeMargin){
                return true;
            }
        }
        return false;
    }

    public void setAdded(boolean added){
        isAdded = added;
    }

    public boolean isAdded(){
        return isAdded;
    }
}
