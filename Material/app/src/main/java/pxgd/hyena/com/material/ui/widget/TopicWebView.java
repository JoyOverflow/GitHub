package pxgd.hyena.com.material.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;

import pxgd.hyena.com.material.model.entity.Reply;
import pxgd.hyena.com.material.model.entity.TopicWithReply;
import pxgd.hyena.com.material.model.util.EntityUtils;
import pxgd.hyena.com.material.ui.jsbridge.FormatJavascriptInterface;
import pxgd.hyena.com.material.ui.jsbridge.ImageJavascriptInterface;
import pxgd.hyena.com.material.ui.jsbridge.TopicJavascriptInterface;

public class TopicWebView extends CNodeWebView {

    private static final String LIGHT_THEME_PATH = "file:///android_asset/topic_light.html";
    private static final String DARK_THEME_PATH = "file:///android_asset/topic_dark.html";

    private boolean pageLoaded = false;
    private TopicWithReply topic = null;
    private String userId = null;

    public TopicWebView(@NonNull Context context) {
        super(context);
    }

    public TopicWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TopicWebView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TopicWebView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("AddJavascriptInterface")
    public void setBridgeAndLoadPage(@NonNull TopicJavascriptInterface topicBridge) {
        addJavascriptInterface(new ImageJavascriptInterface(getContext()), ImageJavascriptInterface.NAME);
        addJavascriptInterface(new FormatJavascriptInterface(), FormatJavascriptInterface.NAME);
        addJavascriptInterface(topicBridge, TopicJavascriptInterface.NAME);
        loadUrl(isDarkTheme() ? DARK_THEME_PATH : LIGHT_THEME_PATH);
    }

    @Override
    protected void onPageFinished(String url) {
        pageLoaded = true;
        if (topic != null) {
            updateTopicAndUserId(topic, userId);
            topic = null;
            userId = null;
        }
    }

    public void updateTopicAndUserId(@NonNull TopicWithReply topic, String userId) {
        if (pageLoaded) {
            topic.getContentHtml(); // 确保Html渲染
            for (Reply reply : topic.getReplyList()) {
                reply.getContentHtml(); // 确保Html渲染
            }
            loadUrl("" +
                    "javascript:\n" +
                    "updateTopicAndUserId(" + EntityUtils.gson.toJson(topic) + ", '" + userId + "');"
            );
        } else {
            this.topic = topic;
            this.userId = userId;
        }
    }

    public void updateTopicCollect(boolean isCollect) {
        if (pageLoaded) {
            loadUrl("" +
                    "javascript:\n" +
                    "updateTopicCollect(" + isCollect + ");"
            );
        }
    }

    public void updateReply(@NonNull Reply reply) {
        if (pageLoaded) {
            reply.getContentHtml(); // 确保Html渲染
            loadUrl("" +
                    "javascript:\n" +
                    "updateReply(" + EntityUtils.gson.toJson(reply) + ");"
            );
        }
    }

    public void appendReply(@NonNull Reply reply) {
        if (pageLoaded) {
            reply.getContentHtml(); // 确保Html渲染
            loadUrl("" +
                    "javascript:\n" +
                    "appendReply(" + EntityUtils.gson.toJson(reply) + ");\n" +
                    "setTimeout(function () {\n" +
                    "    window.scrollTo(0, document.body.clientHeight);\n" +
                    "}, 100);"
            );
        }
    }

}
