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
public class Pipe extends AbsElement implements AbsElement.Movable{

    private Bitmap extraBitmap;

    private int pipeWidth;

    private int pipeMargin;

    //上下管道长度
    private int[] pipeLengths;

    private static final Random random = new Random();

    private Pipe(Context context, int width, int height, Bitmap bitmap) {
        super(context, width, height, bitmap);
    }

    public Pipe(Context context, int width, int height, Bitmap bitmap,Bitmap extraBitmap) {
        this(context, width, height, bitmap);
        this.extraBitmap = extraBitmap;
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        //管道默认从右边出现
        marginLeft = getWidth();
        pipeLengths = getPipeLength();
        pipeWidth = Util.dp2px(getContext(), Constant.PIPE_WIDTH_DIP);
    }

    public int[] getPipeLength(){
        int[] pipes = new int[2];
        pipeMargin = (int)(getHeight()*Constant.PIPE_MARGIN);
        pipes[0] = random.nextInt((int)(getHeight()*(Constant.PIPE_MAX_HEIGHT-Constant.PIPE_MIN_HEIGHT)))+(int)(getHeight()*Constant.PIPE_MIN_HEIGHT);
        //pipes[1] = (int)(getHeight()*Constant.PIPE_SPACE)-pipeMargin-pipeLengths[0];
        pipes[1] = pipes[0];
        return pipes;
    }

    @Override
    public void drawElement(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(marginLeft,-pipeLengths[0]);
        RectF rectTop = new RectF(0,0,pipeWidth,pipeLengths[0]);
        canvas.drawBitmap(getBitmap(),null,rectTop,null);
        canvas.translate(0, pipeLengths[0] + pipeMargin);
        RectF rectBottom = new RectF(0,0,pipeWidth,pipeLengths[1]);
        canvas.drawBitmap(extraBitmap,null,rectBottom,null);
        canvas.restore();
    }

    @Override
    public void verticalMoveBy(int distance) {

    }

    @Override
    public void horizontalMoveBy(int distance) {
        marginLeft+=distance;
    }

    public boolean isOutScreen(){
        return marginLeft+pipeWidth<=0;
    }
}
