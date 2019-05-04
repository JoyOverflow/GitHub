package pxgd.hyena.com.sunset;

import android.support.v4.app.Fragment;

public class MainActivity extends MyActivity {

    @Override
    protected Fragment createFragment() {
        return SunsetFragment.newInstance();
    }
}
