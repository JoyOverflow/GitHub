package pxgd.hyena.com.beatbox;

import android.support.v4.app.Fragment;

public class MainActivity extends MyActivity {
    /**
     * 实现抽象方法，加载指定片段
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }


}
