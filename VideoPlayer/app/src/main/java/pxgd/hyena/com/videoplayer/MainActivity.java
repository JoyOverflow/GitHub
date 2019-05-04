package pxgd.hyena.com.videoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import pxgd.hyena.com.jiaozivideoplayer.Jzvd;
import pxgd.hyena.com.jiaozivideoplayer.JzvdStd;
import pxgd.hyena.com.videoplayer.CustomJzvd.MyJzvdStd;

public class MainActivity extends AppCompatActivity {

    MyJzvdStd myJzvdStd;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myJzvdStd = findViewById(R.id.jz_video);
        myJzvdStd.setUp(
                "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4",
                "测试一下",
                JzvdStd.SCREEN_NORMAL);

        Glide.with(this).load(
                "http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(myJzvdStd.thumbImageView);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.resetAllVideos();
        Log.d(TAG, "onPause！");
    }
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed！");
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }



    /**
     * 按钮的触发事件
     * @param view
     */
    public void clickApi(View view) {
        startActivity(new Intent(MainActivity.this, ApiActivity.class));
    }
    public void clickListView(View view) {
        startActivity(new Intent(MainActivity.this, ListViewActivity.class));
    }
    public void clickTinyWindow(View view) {
        startActivity(new Intent(MainActivity.this, TinyWindowActivity.class));
    }
    public void clickDirectPlay(View view) {
        startActivity(new Intent(MainActivity.this, DirectPlayActivity.class));
    }
    public void clickWebView(View view) {
        startActivity(new Intent(MainActivity.this, WebViewActivity.class));
    }
    public void clickWebClient(View view) {
        startActivity(new Intent(MainActivity.this, WebClientActivity.class));
    }
}
