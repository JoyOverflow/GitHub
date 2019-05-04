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

import pxgd.hyena.com.material.ui.jsbridge.ImageJavascriptInterface;

public class ContentWebView extends CNodeWebView {

    private static final String HTML_0 = "" +
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\">\n";

    private static final String LIGHT_THEME_CSS = "" +
            "<link type=\"text/css\" rel=\"stylesheet\" href=\"" + "file:///android_asset/css/content_light.css" + "\">\n" +
            "<link type=\"text/css\" rel=\"stylesheet\" href=\"" + "file:///android_asset/css/markdown_light.css" + "\">\n";

    private static final String DARK_THEME_CSS = "" +
            "<link type=\"text/css\" rel=\"stylesheet\" href=\"" + "file:///android_asset/css/content_dark.css" + "\">\n" +
            "<link type=\"text/css\" rel=\"stylesheet\" href=\"" + "file:///android_asset/css/markdown_dark.css" + "\">\n";

    private static final String HTML_1 = "" +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"markdown-container\">\n";

    private static final String HTML_2 = "" +
            "</div>\n" +
            "<script>\n" +
            "(function() {\n" +
            "    var markdownContainer = document.getElementById('markdown-container');\n" +
            "    markdownContainer.onclick = function (event) {\n" +
            "        if (event.target.nodeName === 'IMG') {\n" +
            "            if (event.target.parentNode.nodeName !== 'A') {\n" +
            "                window." + ImageJavascriptInterface.NAME + ".openImage(event.target.src);\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "})();\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";

    public ContentWebView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ContentWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ContentWebView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ContentWebView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @SuppressLint("AddJavascriptInterface")
    private void init(@NonNull Context context) {
        addJavascriptInterface(new ImageJavascriptInterface(context), ImageJavascriptInterface.NAME);
    }

    @NonNull
    protected String getThemeCssHtmlSlice(boolean isDarkTheme) {
        return isDarkTheme ? DARK_THEME_CSS : LIGHT_THEME_CSS;
    }

    public void loadRenderedContent(String data) {
        data = HTML_0 + getThemeCssHtmlSlice(isDarkTheme()) + HTML_1 + data + "\n" + HTML_2;
        loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }

}
