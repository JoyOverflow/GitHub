package pxgd.hyena.com.criminaler;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends MyActivity {

    //列表片段的项点击时，向活动传送crimeId的键名
    public static final String EXTRA_CRIME_ID = "pxgd.hyena.com.criminal.crime_id";

    /**
     * 调用超类的方法
     * @param savedInstanceState
     */
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
        //从意图中取得guid，然后返回片段类实例
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
