package pxgd.hyena.com.lovepet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

import pxgd.hyena.com.lovepet.config.Config;
import pxgd.hyena.com.lovepet.utils.StatusBarUtils;

public class SplashActivity extends AppCompatActivity {

    /**
     * SplashActivity活动类创建
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fresco.initialize(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorWhite);

        setContentView(R.layout.activity_splash);

        //延时一秒执行Handler类的handleMessage方法
        mHandler.sendEmptyMessageDelayed(
                Config.SUCCESS_INT,
                1000
        );
    }
    /**
     * 创建一个Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //发送意图
            startActivity(
                    new Intent(SplashActivity.this, LeaderActivity.class)
            );
            //关闭当前活动
            finish();
        }
    };
}
