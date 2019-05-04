package pxgd.hyena.com.gallery;

import android.support.v4.app.Fragment;

public class MainActivity extends MyActivity {

    /**
     * 当前活动要加载的片段
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return GalleryFragment.newInstance();
    }

}
