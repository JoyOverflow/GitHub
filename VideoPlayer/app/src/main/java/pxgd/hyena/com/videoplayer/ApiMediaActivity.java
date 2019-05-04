package pxgd.hyena.com.videoplayer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import pxgd.hyena.com.jiaozivideoplayer.JZDataSource;
import pxgd.hyena.com.jiaozivideoplayer.JZMediaSystem;
import pxgd.hyena.com.jiaozivideoplayer.Jzvd;
import pxgd.hyena.com.jiaozivideoplayer.JzvdStd;
import pxgd.hyena.com.videoplayer.CustomMedia.JZMediaSystemAssertFolder;

public class ApiMediaActivity extends AppCompatActivity {

    JzvdStd jzvdStd;
    Handler handler = new Handler();//这里其实并不需要handler，为了防止播放中切换播放器引擎导致的崩溃，实际使用时一般不会遇到，可以随时调用JZVideoPlayer.setMediaInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("CustomMediaPlayer");
        setContentView(R.layout.activity_api_media);


        jzvdStd = findViewById(R.id.videoplayer);
        JZDataSource jzDataSource = null;
        try {
            jzDataSource = new JZDataSource(getAssets().openFd("local_video.mp4"));
            jzDataSource.title = "饺子快长大";
        } catch (IOException e) {
            e.printStackTrace();
        }
        jzvdStd.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL, new JZMediaSystemAssertFolder(jzvdStd));
//        jzvdStd.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
//                , "饺子快长大", JzvdStd.SCREEN_NORMAL, new JZMediaIjk(jzvdStd));
        Glide.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzvdStd.thumbImageView);
    }

    public void clickChangeToIjkplayer(View view) {
        /*
        Jzvd.resetAllVideos();

        jzvdStd.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , "饺子快长大", JzvdStd.SCREEN_NORMAL, new JZMediaIjk(jzvdStd));
        jzvdStd.startVideo();
        Toast.makeText(this, "Change to Ijkplayer", Toast.LENGTH_SHORT).show();
        */
    }

    public void clickChangeToSystem(View view) {
        Jzvd.resetAllVideos();
        jzvdStd.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , "饺子快长大", JzvdStd.SCREEN_NORMAL, new JZMediaSystem(jzvdStd));
        jzvdStd.startVideo();
        Toast.makeText(this, "Change to MediaPlayer", Toast.LENGTH_SHORT).show();
    }

    public void clickChangeToExo(View view) {
        /*
        Jzvd.resetAllVideos();
        jzvdStd.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , "饺子快长大", JzvdStd.SCREEN_NORMAL, new JZMediaExo(jzvdStd));
        jzvdStd.startVideo();
        Toast.makeText(this, "Change to ExoPlayer", Toast.LENGTH_SHORT).show();
        */
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

}
