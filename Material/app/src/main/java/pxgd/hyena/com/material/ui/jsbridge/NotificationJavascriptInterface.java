package pxgd.hyena.com.material.ui.jsbridge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

import pxgd.hyena.com.material.UserDetailActivity;
import pxgd.hyena.com.material.ui.util.Navigator;


public final class NotificationJavascriptInterface {

    public static final String NAME = "notificationBridge";

    private final Context context;

    public NotificationJavascriptInterface(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @JavascriptInterface
    public void openTopic(String topicId) {
        Navigator.TopicWithAutoCompat.start(context, topicId);
    }

    @JavascriptInterface
    public void openUser(String loginName) {
        UserDetailActivity.start(context, loginName);
    }

}
