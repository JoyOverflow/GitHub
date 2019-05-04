package pxgd.hyena.com.launcher;
import android.support.v4.app.Fragment;

public class MainActivity extends MyActivity {

    /**
     * 返回当前活动需加载的片段
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return LauncherFragment.newInstance();
    }
}
