package pxgd.hyena.com.criminaler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class CrimeListActivity
        extends MyActivity
        implements CrimeListFragment.Callbacks,CrimeFragment.Callbacks2{

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

    @Override
    protected int getLayoutResId() {
        //使用别名资源ID
        return R.layout.activity_masterdetail;
    }

    /**
     * 实现片段定义的接口
     * @param crime
     */
    @Override
    public void onCrimeSelected(Crime crime) {
        //判断是否存在第二个FrameLayout布局控件（存在则为平板布局）
        if (findViewById(R.id.detail_container) == null) {
            //启动第二个活动
            Intent intent = new Intent(this, CrimePagerActivity.class);
            intent.putExtra(CrimePagerActivity.EXTRA_CRIME_ID, crime.getId());
            startActivity(intent);

        } else {
            //创建片段事务，加载指定片段到FrameLayout布局控件中
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, newDetail)
                    .commit();
        }
    }

    /**
     * 实现片段定义的接口（刷新行为列表）
     * @param crime
     */
    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment =
                (CrimeListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.container);
        listFragment.updateUI();
    }
}
