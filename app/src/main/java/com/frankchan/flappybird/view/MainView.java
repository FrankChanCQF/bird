package com.frankchan.flappybird.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.frankchan.flappybird.Constant;
import com.frankchan.flappybird.Util.Util;
import com.frankchan.flappybird.element.AbsElement;
import com.frankchan.flappybird.element.Background;
import com.frankchan.flappybird.element.Bird;
import com.frankchan.flappybird.element.Floor;
import com.frankchan.flappybird.element.Pipe;

/**
 * Created by frankchan on 2015/2/9.
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private SurfaceHolder mHolder;

    private Thread mThread;

    private Canvas mCanvas;

    private boolean mRunnable;

    private int mWidth,mHeight;

    private Floor mFloor;

    private Pipe mPipe;

    private Bird mBird;

    private AbsElement mBackground;

    private int mHorizontalSpeed,mDropSpeed;

    private Bitmap bmpBg,bmpTop,bmpBottom,bmpFloor,bmpBird;

    public MainView(Context context) {
        this(context, null);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mThread = new Thread(this);
        mHolder.addCallback(this);
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        setKeepScreenOn(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void run() {
        while(isRunning()){
            long startTime = System.currentTimeMillis();
            actionDraw();
            long endTime = System.currentTimeMillis();
            try{
                if(endTime-startTime< Constant.VIEW_REFRESH_INTERVAL){
                    Thread.sleep(Constant.VIEW_REFRESH_INTERVAL - endTime + startTime);
                }
                refreshNextFrame();
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    private void actionDraw(){
        try{
            mCanvas = mHolder.lockCanvas();
            if(mCanvas!=null) {
                drawAllElements();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(mCanvas!=null){
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void initAllElements(){
        mBackground = new Background(getContext(),mWidth,mHeight,bmpBg);
        mBird = new Bird(getContext(),mWidth,mHeight,bmpBird);
        mFloor = new Floor(getContext(),mWidth,mHeight,bmpFloor);
        mPipe = new Pipe(getContext(),mWidth,mHeight,bmpTop, bmpBottom);
    }

    private void initBitmaps(){
        bmpBg = loadImageByResource(Constant.RES_BACKGROUND);
        bmpBird = loadImageByResource(Constant.RES_BIRD);
        bmpFloor = loadImageByResource(Constant.RES_FLOOR);
        bmpTop = loadImageByResource(Constant.RES_TOP_PIPE);
        bmpBottom = loadImageByResource(Constant.RES_BOTTOM_PIPE);
    }

    private void drawAllElements(){
        if(mBackground==null||mBird==null||mFloor==null)
            return;
            mBackground.drawElement(mCanvas);
            mBird.drawElement(mCanvas);
            mPipe.drawElement(mCanvas);
            mFloor.drawElement(mCanvas);
    }

    //调整下一次刷新的数据
    private void refreshNextFrame(){
        mPipe.horizontalMoveBy(mHorizontalSpeed);
        mFloor.horizontalMoveBy(mHorizontalSpeed);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initBitmaps();
        mHorizontalSpeed = -Util.dp2px(getContext(),Constant.VIEW_MOVE_SPEED_DIP);
        mDropSpeed = Util.dp2px(getContext(),Constant.BIRD_DROP_SPEED_DIP);
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mWidth  = width;
        mHeight = height;
        initAllElements();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { release();}


    private Bitmap loadImageByResource(int resId){
        return BitmapFactory.decodeResource(getResources(),resId);
    }


    public void start(){
        mRunnable = true;
        mThread.start();
    }

    public boolean isRunning(){
        return mRunnable;
    }

    public void pause(){ }

    public void release(){
        mRunnable = false;
    }
}
