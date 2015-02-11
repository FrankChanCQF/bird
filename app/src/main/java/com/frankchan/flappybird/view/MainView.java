package com.frankchan.flappybird.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.frankchan.flappybird.Constant;
import com.frankchan.flappybird.Util.Util;
import com.frankchan.flappybird.element.Background;
import com.frankchan.flappybird.element.Bird;
import com.frankchan.flappybird.element.Floor;
import com.frankchan.flappybird.element.Grade;
import com.frankchan.flappybird.element.Pipe;

import java.util.ArrayList;

/**
 * Created by frankchan on 2015/2/9.
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback,Runnable,View.OnTouchListener{

    /*界面部分*/

    private SurfaceHolder mHolder;

    private Thread mThread;

    private Canvas mCanvas;

    private boolean mRunnable;

    private int mWidth,mHeight;

    private Grade mGrade;

    private Bird mBird;

    private Floor mFloor;

    private Background mBackground;

    private ArrayList<Pipe>mPipes = new ArrayList<>();

    private ArrayList<Pipe>mRemovePipes = new ArrayList<>();

    private int mHorizontalSpeed;

    private Bitmap bmpBg,bmpTop,bmpBottom,bmpFloor,bmpBird;

    private Bitmap[] bmpNumArrays = new Bitmap[Constant.RES_NUMBER_ARRAYS.length];

    /*逻辑部分*/

    private int mPipePaceSum;

    private enum Status{
        Waiting,Running,Over
    }

    private Status mStatus = Status.Running;

    private boolean mEnableTouch = true;

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
        setOnTouchListener(this);
    }

    @Override
    public void run() {
        while(isRunning()){
            actionLogic();
            long startTime = System.currentTimeMillis();
            actionDraw();
            long endTime = System.currentTimeMillis();
            try{
                if(endTime-startTime< Constant.VIEW_REFRESH_INTERVAL){
                    Thread.sleep(Constant.VIEW_REFRESH_INTERVAL - endTime + startTime);
                }
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    private void actionLogic(){
        switch(mStatus){
            case Running:
                //刷新下一帧
                mBird.autoDrop();
                mFloor.horizontalMoveBy(mHorizontalSpeed);
                //碰撞检测
                boolean isCrashPipe = false;
                boolean isCrashFloor = mFloor.isCrash(mBird);
                for(Pipe pipe:mPipes){
                    pipe.horizontalMoveBy(mHorizontalSpeed);
                    if(pipe.isOutScreen()){
                        mRemovePipes.add(pipe);
                    }else if(pipe.isCrash(mBird)){
                        isCrashPipe = true;
                    }
                    if(pipe.getRight()<mBird.getLeft()&&!pipe.isAdded()){
                        mGrade.increase();
                        pipe.setAdded(true);
                    }
                }
                if(isCrashFloor||isCrashPipe){
                    mStatus = Status.Over;
                }
                mPipes.remove(mRemovePipes);

                mPipePaceSum +=mHorizontalSpeed;
                if(Math.abs(mPipePaceSum) >=getWidth()*Constant.PIPE_HORIZONTAL_MARGIN){
                    mPipes.add(new Pipe(getContext(),getWidth(),getHeight(),bmpTop,bmpBottom));
                    mPipePaceSum=0;
                }
                break;
            case Waiting:
                mEnableTouch = false;
                break;
            case Over:
                mEnableTouch = false;
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        restart();
                        mEnableTouch = true;
                    }
                },1000);
                break;
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
        mGrade = new Grade(getContext(),mWidth,mHeight,bmpNumArrays);
        mPipes.add(new Pipe(getContext(),mWidth,mHeight,bmpTop, bmpBottom));
    }

    private void initBitmaps(){
        bmpBg = loadImageByResource(Constant.RES_BACKGROUND);
        bmpBird = loadImageByResource(Constant.RES_BIRD);
        bmpFloor = loadImageByResource(Constant.RES_FLOOR);
        bmpTop = loadImageByResource(Constant.RES_TOP_PIPE);
        bmpBottom = loadImageByResource(Constant.RES_BOTTOM_PIPE);
        for(int i=0;i<Constant.RES_NUMBER_ARRAYS.length;i++){
            bmpNumArrays[i] = loadImageByResource(Constant.RES_NUMBER_ARRAYS[i]);
        }
    }

    private void drawAllElements(){
        if(mBackground==null||mBird==null||mFloor==null||mGrade==null||mPipes.size()==0) {
            return;
        }
            mBackground.drawElement(mCanvas);
            mBird.drawElement(mCanvas);
            mFloor.drawElement(mCanvas);
        for(Pipe pipe:mPipes){
            pipe.drawElement(mCanvas);
        }
        mGrade.drawElement(mCanvas);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initBitmaps();
        mHorizontalSpeed = -Util.dp2px(getContext(),Constant.VIEW_MOVE_SPEED_DIP);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mWidth  = width;
        mHeight = height;
        initAllElements();
        if(!isRunning())
            start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { pause();}


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

    public void pause(){
        mRunnable = false;
    }

    public void restart(){
        pause();
        mPipes.clear();
        initAllElements();
        mRunnable = true;
        mStatus = Status.Running;
        mPipePaceSum = 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN&&mEnableTouch){
            mBird.raiseByTouch();
        }
        return true;
    }
}
