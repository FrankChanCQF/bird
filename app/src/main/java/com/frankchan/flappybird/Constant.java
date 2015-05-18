package com.frankchan.flappybird;

/**
 * Created by frankchan on 2015/2/9.
 */
public class Constant {

    //视图最小刷新间隔
    public static final int VIEW_REFRESH_INTERVAL = 50;

    //每次刷新移动距离
    public static final int VIEW_MOVE_SPEED_DIP = 2;

    //<----背景----->
    public static final int RES_BACKGROUND = R.drawable.background;

    //<----小鸟----->
    public static final int RES_BIRD = R.drawable.bird;
    public static final float BIRD_MARGIN_TOP = 1/2f;
    public static final int BIRD_MAX_ROTATION = 45;
    public static final int BIRD_RAISE_ROTATION = 8;
    public static final int BIRD_LOWER_ROTATION = 1;

    public static final int BIRD_SIZE_DIP = 30;
    public static final int BIRD_DROP_SPEED_DIP = 3;
    public static final int BIRD_TOUCH_HEIGHT_DIP = 30;

    //<----分数----->
    public static final int[] RES_NUMBER_ARRAYS = {R.drawable.n0,R.drawable.n1,R.drawable.n2,
            R.drawable.n3,R.drawable.n4,R.drawable.n5,R.drawable.n6,R.drawable.n7,R.drawable.n8,R.drawable.n9};
    public static final float NUMBER_MARGIN_TOP = 1/5f;
    public static final int NUMBER_SINGLE_WIDTH_DIP = 25;

    //<----地板----->

    public static final int RES_FLOOR = R.drawable.floor;;
    public static final float FLOOR_MARGIN_TOP = 4/5f;

    //<----管道----->

    public static final int RES_TOP_PIPE = R.drawable.top;
    public static final int RES_BOTTOM_PIPE = R.drawable.bottom;

    public static final float PIPE_MAX_HEIGHT = 2/5f;
    public static final float PIPE_MIN_HEIGHT = 1/6f;
    public static final float PIPE_VERTICAL_MARGIN = 1/5f;
    public static final float PIPE_HORIZONTAL_MARGIN = 1/2f;
    //管道绘图占据总空间
    public static final float PIPE_SPACE = 4/5f;

    public static final int PIPE_WIDTH_DIP = 60;
}
