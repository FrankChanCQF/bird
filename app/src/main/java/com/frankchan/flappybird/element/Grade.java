package com.frankchan.flappybird.element;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.frankchan.flappybird.Constant;
import com.frankchan.flappybird.Util.Util;

import java.util.ArrayList;

/**
 * Created by frankchan on 2015/2/11.
 */
public class Grade extends AbsElement {

    private int mGrade = 0;

    private Bitmap[] bmpArrays;

    private int charWidth,charHeight;

    private ArrayList<Integer>numArrays = new ArrayList<>();


    public Grade(Context context, int width, int height, Bitmap[] bitmapArray){
        super(context, width, height, null);
        bmpArrays = bitmapArray;
        onCreate(context);
    }

    @Override
    public void onCreate(Context context) {
        if(bmpArrays!=null) {
            charWidth = Util.dp2px(getContext(), Constant.NUMBER_SINGLE_WIDTH_DIP);
            charHeight = charWidth * bmpArrays[0].getHeight() / bmpArrays[0].getWidth();
            top = getCanvasHeight() * Constant.NUMBER_MARGIN_TOP;
            reset();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        left = getCanvasWidth()/2-numArrays.size()*charWidth/2;
        for(int j =0;j<numArrays.size();j++){
            mRectF.set(left+j*charWidth,top,left+(j+1)*charWidth,top+charHeight);
            canvas.drawBitmap(bmpArrays[numArrays.get(j)],null,mRectF,null);
        }
        canvas.restore();
    }

    @Override
    public void onDestroy() {
        for (Bitmap bitmap :bmpArrays){
            recycleBitmap(bitmap);
        }
    }

    private void computeGrade(){
        String str = String.valueOf(mGrade);
        numArrays.clear();
        for(int i = 0;i<str.length();i++){
            numArrays.add(Integer.parseInt(String.valueOf(str.charAt(i))));
        }
    }

    public int getGrade(){
        return mGrade;
    }

    public void increase(){
        mGrade++;
        computeGrade();
    }
    public void reset(){
        mGrade = 0;
        computeGrade();
    }
}
