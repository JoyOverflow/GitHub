package pxgd.hyena.com.guanggoo;

import android.app.Application;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */
public class MyApplication extends Application {

    private static Application sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
    public static Application getInstance() {
        return sInstance;
    }
}
