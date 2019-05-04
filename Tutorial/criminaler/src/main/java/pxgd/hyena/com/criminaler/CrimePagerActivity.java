package pxgd.hyena.com.criminaler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * 加载CrimeFragment片段
 */
public class CrimePagerActivity extends AppCompatActivity
        implements CrimeFragment.Callbacks2{

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    //列表片段的项点击时，向活动传送crimeId的键名
    public static final String EXTRA_CRIME_ID = "pxgd.hyena.com.criminal.crime_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        //得到行为集合
        mCrimes = CrimeLab.get(this).getCrimes();
        //得到意图传送来的GUID
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        //查找布局内的ViewPager视图（来源于v4支持库）
        mViewPager = findViewById(R.id.activity_crime_pager_view_pager);

        //得到片段管理器（ViewPager视图也需设定适配器）
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            //获取集合中的元素数（列表项数目）
            @Override
            public int getCount() {
                return mCrimes.size();
            }
            //通过指定索引处的对象，返回要加载的片段实例
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }
        });

        //设置ViewPager的当前显示项（否则只默认显示第一个列表项）
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }

    @Override
    public void onCrimeUpdated(Crime crime) {

    }
}
