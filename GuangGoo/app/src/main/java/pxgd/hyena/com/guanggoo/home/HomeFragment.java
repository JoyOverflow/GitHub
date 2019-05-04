package pxgd.hyena.com.guanggoo.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import pxgd.hyena.com.guanggoo.R;
import pxgd.hyena.com.guanggoo.base.BaseFragment;
import pxgd.hyena.com.guanggoo.router.annotations.ClearTop;
import pxgd.hyena.com.guanggoo.router.annotations.StartsWithAppBar;


/**
 * @author mazhuang
 */
@ClearTop
@StartsWithAppBar
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tab) TabLayout mTabLayout;
    @BindView(R.id.pager) ViewPager mViewPager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);
        
        initViews();

        return root;
    }

    private void initViews() {
        HomePagerAdapter adapter = new HomePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public String getTitle() {
        return getString(R.string.app_name);
    }
}
