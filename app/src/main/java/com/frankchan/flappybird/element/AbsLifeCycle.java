package com.frankchan.flappybird.element;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by frankchan on 2015/3/7.
 * Life cycle interface of all elements for Unified handle
 */
public interface AbsLifeCycle {

    public void onCreate(Context context);

    public void onDraw(Canvas canvas);

    public void onDestroy();

}
