package pxgd.hyena.com.videoplayer;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import pxgd.hyena.com.jiaozivideoplayer.Jzvd;
import pxgd.hyena.com.videoplayer.demo.AdapterVideoList;

public class ListViewNormalActivity extends AppCompatActivity {

    ListView listView;
    SensorManager sensorManager;
    Jzvd.JZAutoFullscreenListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("NormalListView");
        setContentView(R.layout.activity_list_view_normal);

        listView = findViewById(R.id.listview);
        listView.setAdapter(new AdapterVideoList(this,
                VideoConstant.videoUrls[0],
                VideoConstant.videoTitles[0],
                VideoConstant.videoThumbs[0]));

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (Jzvd.CURRENT_JZVD == null) return;
                int lastVisibleItem = firstVisibleItem + visibleItemCount;
                int currentPlayPosition = Jzvd.CURRENT_JZVD.positionInList;
//                Log.e(TAG, "onScrollReleaseAllVideos: " +
//                        currentPlayPosition + " " + firstVisibleItem + " " + currentPlayPosition + " " + lastVisibleItem);
                if (currentPlayPosition >= 0) {
                    if ((currentPlayPosition < firstVisibleItem || currentPlayPosition > (lastVisibleItem - 1))) {
                        if (Jzvd.CURRENT_JZVD.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                            Jzvd.resetAllVideos();//为什么最后一个视频横屏会调用这个，其他地方不会
                        }
                    }
                }
//                Jzvd.onScrollReleaseAllVideos(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new Jzvd.JZAutoFullscreenListener();
    }
}
