package pxgd.hyena.com.material;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import pxgd.hyena.com.material.util.ResUtils;


public abstract class StatusBarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            FrameLayout rootView = new FrameLayout(this);
            rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            View contentView = LayoutInflater.from(this).inflate(layoutResId, rootView, false);
            contentView.setFitsSystemWindows(true);
            rootView.addView(contentView);

            View statusBarView = new View(this);
            statusBarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ResUtils.getStatusBarHeight(this)));
            statusBarView.setBackgroundColor(ResUtils.getThemeAttrColor(this, R.attr.colorPrimaryDark));
            rootView.addView(statusBarView);

            super.setContentView(rootView);
        } else {
            super.setContentView(layoutResId);
        }
    }
    public void setFitsStatusBarMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }




}
