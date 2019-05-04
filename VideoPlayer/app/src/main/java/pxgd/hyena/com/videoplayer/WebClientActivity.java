package pxgd.hyena.com.videoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import pxgd.hyena.com.videoplayer.demo.CustomWebClient;

public class WebClientActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_client);

        mWebView = findViewById(R.id.main_webview);

        //启用Js
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //(1)打开远程Url
        mWebView.loadUrl("https://www.h5h4.com/");
        mWebView.setWebViewClient(new CustomWebClient());

        //(2)打开本地网页
        //mWebView.loadUrl("file:///android_asset/index.html");
    }
    @Override
    public void onBackPressed() {
        //处理浏览器的回退
        if(mWebView.canGoBack())
            mWebView.goBack();
         else
            super.onBackPressed();
    }
}
