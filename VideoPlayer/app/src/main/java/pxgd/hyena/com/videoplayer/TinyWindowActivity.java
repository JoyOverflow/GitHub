package pxgd.hyena.com.videoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import pxgd.hyena.com.jiaozivideoplayer.Jzvd;
import pxgd.hyena.com.jiaozivideoplayer.JzvdStd;

public class TinyWindowActivity extends AppCompatActivity {

    JzvdStd mJzvdStd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("TinyWindow");
        setContentView(R.layout.activity_tiny_window);

        mJzvdStd = findViewById(R.id.jz_video);
        mJzvdStd.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4", "饺子快长大"
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(mJzvdStd.thumbImageView);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.resetAllVideos();
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
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
    public void clickTinyWindow(View view) {
        mJzvdStd.gotoScreenTiny();
    }

    public void clickAutoTinyListView(View view) {
        Toast.makeText(this, "comming soon", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, ActivityTinyWindowListViewNormal.class));
    }

    public void clickAutoTinyListViewMultiHolder(View view) {
        Toast.makeText(this, "comming soon", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, ActivityTinyWindowListViewMultiHolder.class));
    }

    public void clickAutoTinyListViewRecyclerView(View view) {
        Toast.makeText(this, "comming soon", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, ActivityTinyWindowRecycleView.class));
    }

    public void clickAutoTinyListViewRecyclerViewMultiHolder(View view) {
        Toast.makeText(this, "comming soon", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, ActivityTinyWindowRecycleViewMultiHolder.class));
    }
}
