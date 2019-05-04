package pxgd.hyena.com.material.ui.jsbridge;

import android.webkit.JavascriptInterface;

import org.joda.time.DateTime;

import pxgd.hyena.com.material.util.FormatUtils;

public final class FormatJavascriptInterface {

    public static final String NAME = "formatBridge";

    @JavascriptInterface
    public String getRelativeTimeSpanString(String time) {
        return FormatUtils.getRelativeTimeSpanString(new DateTime(time));
    }

}
