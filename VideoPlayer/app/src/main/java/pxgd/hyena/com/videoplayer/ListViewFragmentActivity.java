package pxgd.hyena.com.videoplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import pxgd.hyena.com.jiaozivideoplayer.Jzvd;
import pxgd.hyena.com.videoplayer.demo.FragmentDemo;

public class ListViewFragmentActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    List<FragmentDemo> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_fragment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("ActivityListViewFragmentViewPager");
        fragmentList.add(new FragmentDemo().setIndex(0));
        fragmentList.add(new FragmentDemo().setIndex(1));
        fragmentList.add(new FragmentDemo().setIndex(2));
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(myAdapter);
        viewPager.setOnPageChangeListener(this);
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

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        Jzvd.resetAllVideos();
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
