package pxgd.hyena.com.multiple;

import android.app.Application;

import pxgd.hyena.com.multiple.color.util.SharedPreferencesMgr;

/**
 * Created by chengli on 15/6/14.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesMgr.init(this, "derson");
    }
}
