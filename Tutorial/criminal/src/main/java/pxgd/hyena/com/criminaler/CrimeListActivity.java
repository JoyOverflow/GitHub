package pxgd.hyena.com.criminaler;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class CrimeListActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 返回当前活动要加载的片段类对象
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
