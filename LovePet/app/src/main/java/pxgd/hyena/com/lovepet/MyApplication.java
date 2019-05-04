package pxgd.hyena.com.lovepet;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

import pxgd.hyena.com.autolayout.config.AutoLayoutConifg;

public class MyApplication extends Application {

    public static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();

        //Fresco图像读取模块
        Fresco.initialize(this);
        /*
        ImagePipelineConfig config =
                ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build();
        Fresco.initialize(this,config);
        */

        AutoLayoutConifg.getInstance().useDeviceSize();
        Log.d(TAG, "MyApplication onCreate！");
    }
}
