package com.frankchan.flappybird;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.frankchan.flappybird.view.MainView;


public class MainActivity extends Activity {


    private MainView mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mView = new MainView(this);
        setContentView(mView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView.recycle();
    }
}
