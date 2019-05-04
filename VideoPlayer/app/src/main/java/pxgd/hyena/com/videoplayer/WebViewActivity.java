package pxgd.hyena.com.videoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import pxgd.hyena.com.jiaozivideoplayer.JZUtils;
import pxgd.hyena.com.jiaozivideoplayer.Jzvd;
import pxgd.hyena.com.jiaozivideoplayer.JzvdStd;

public class WebViewActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("WebView");
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_web_view);

        mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JZCallBack(), "jzvd");
        mWebView.loadUrl("file:///android_asset/jzvd.html");
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.resetAllVideos();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class JZCallBack {
        @JavascriptInterface
        public void adViewJiaoZiVideoPlayer(final int width, final int height, final int top, final int left, final int index) {
            runOnUiThread(() -> {
                if (index == 0) {
                    JzvdStd jzvdStd = new JzvdStd(WebViewActivity.this);
                    jzvdStd.setUp(VideoConstant.videoUrlList[1], "饺子骑大马",
                            Jzvd.SCREEN_NORMAL);
                    Glide.with(WebViewActivity.this)
                            .load(VideoConstant.videoThumbList[1])
                            .into(jzvdStd.thumbImageView);
                    ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                    layoutParams.y = JZUtils.dip2px(WebViewActivity.this, top);
                    layoutParams.x = JZUtils.dip2px(WebViewActivity.this, left);
                    layoutParams.height = JZUtils.dip2px(WebViewActivity.this, height);
                    layoutParams.width = JZUtils.dip2px(WebViewActivity.this, width);

                    LinearLayout linearLayout = new LinearLayout(WebViewActivity.this);
                    linearLayout.addView(jzvdStd);
                    mWebView.addView(linearLayout, layoutParams);
                } else {
                    JzvdStd jzvdStd = new JzvdStd(WebViewActivity.this);
                    jzvdStd.setUp(VideoConstant.videoUrlList[2], "饺子失态了",
                            Jzvd.SCREEN_NORMAL);
                    Glide.with(WebViewActivity.this)
                            .load(VideoConstant.videoThumbList[2])
                            .into(jzvdStd.thumbImageView);
                    ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                    layoutParams.y = JZUtils.dip2px(WebViewActivity.this, top);
                    layoutParams.x = JZUtils.dip2px(WebViewActivity.this, left);
                    layoutParams.height = JZUtils.dip2px(WebViewActivity.this, height);
                    layoutParams.width = JZUtils.dip2px(WebViewActivity.this, width);

                    LinearLayout linearLayout = new LinearLayout(WebViewActivity.this);
                    linearLayout.addView(jzvdStd);
                    mWebView.addView(linearLayout, layoutParams);
                }

            });

        }
    }




}
