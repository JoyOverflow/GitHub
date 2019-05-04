package pxgd.hyena.com.lovepet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.autolayout.AutoLinearLayout;
import pxgd.hyena.com.lovepet.config.Config;
import pxgd.hyena.com.lovepet.utils.StatusBarUtils;

public class LeaderActivity extends AppCompatActivity {

    @BindView(R.id.leader_img)
    ViewPager mLeaderImg;
    @BindView(R.id.leader_circle)
    AutoLinearLayout mLeaderCircle;
    @BindView(R.id.leader_red)
    ImageView mLeaderRed;

    private List<View> mViewList;
    private View mView;
    private int left;//左边间距
    private int mImgIndex = 0;
    private ValueAnimator animator;
    public static LeaderActivity instance = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorWhite);
        setContentView(R.layout.activity_leader);

        ButterKnife.bind(this);
        instance = this;
        init();
    }


    private void init()
    {
        initCircles();
        mLeaderImg.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float leftMargin = left * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLeaderRed.getLayoutParams();
                params.leftMargin = Math.round(leftMargin);
                mLeaderRed.setLayoutParams(params);
            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        startAnimation(0);
                        break;
                    case 1:
                        startAnimation(1);
                        break;
                    case 2:
                        startAnimation(2);
                        break;
                    case 3:
                        startAnimation(3);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mLeaderImg.setAdapter(new LeaderAdapter());
        startAnimation(0);
    }
    private void initCircles()
    {
        //引导页面
        mViewList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mView = LayoutInflater.from(this).inflate(R.layout.leader_viewpager, null);
            TextView top =  mView.findViewById(R.id.leader_top);
            TextView second =  mView.findViewById(R.id.leader_second);
            top.setText(Config.LEADER_TOP[i]);
            second.setText(Config.LEADER_SECONG[i]);
            mViewList.add(mView);
        }

        mLeaderRed.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        left = mLeaderCircle.getChildAt(1).getLeft() -
                                mLeaderCircle.getChildAt(0).getLeft();
                    }
                }
        );
    }



    private void startAnimation(final int position)
    {
        final ImageView simple = mViewList.get(position).findViewById(R.id.leader_img);
        animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.setDuration(50);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
        animator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationRepeat(Animator animation)
            {
                super.onAnimationRepeat(animation);


                switch (position)
                {
                    case 0:
                        Log.d(MyApplication.TAG, "addListener：" + mImgIndex);
                        simple.setImageResource(Config.IMG_ONE[mImgIndex]);


                        if (mImgIndex == Config.IMG_ONE.length - 1) {
                            mImgIndex = 0;
                            animator.end();
                        }
                        break;
                    case 1:
                        simple.setImageResource(Config.IMG_TWO[mImgIndex]);
                        if (mImgIndex == Config.IMG_TWO.length - 1) {
                            mImgIndex = 0;
                            animator.end();
                        }
                        break;
                    case 2:
                        simple.setImageResource(Config.IMG_THREE[mImgIndex]);
                        if (mImgIndex == Config.IMG_THREE.length - 1) {
                            mImgIndex = 0;
                            animator.end();
                        }
                        break;
                    case 3:
                        simple.setImageResource(Config.IMG_FOUR[mImgIndex]);
                        if (mImgIndex == Config.IMG_FOUR.length - 1) {
                            mImgIndex = 0;
                            animator.end();
                        }
                        break;
                    default:
                        break;
                }
                mImgIndex++;
            }
        });
        animator.start();
    }

    /**
     * 转向注册和登录界面
     * @param view
     */
    @OnClick({R.id.leader_register, R.id.leader_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leader_register:
                startActivity(new Intent(instance, RegisterActivity.class));
                finish();
                break;
            case R.id.leader_login:
                startActivity(new Intent(LeaderActivity.this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }


    private class LeaderAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 4;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
