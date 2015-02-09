package com.frankchan.flappybird.Util;

import android.content.Context;

/**
 * Created by frankchan on 2015/2/9.
 */
public class Util {
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
