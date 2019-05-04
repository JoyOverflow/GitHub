package pxgd.hyena.com.material;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.material.model.api.ApiDefine;
import pxgd.hyena.com.material.model.entity.Topic;
import pxgd.hyena.com.material.model.entity.User;
import pxgd.hyena.com.material.model.glide.GlideApp;
import pxgd.hyena.com.material.presenter.contract.IUserDetailPresenter;
import pxgd.hyena.com.material.presenter.implement.UserDetailPresenter;
import pxgd.hyena.com.material.ui.adapter.UserDetailPagerAdapter;
import pxgd.hyena.com.material.ui.listener.NavigationFinishClickListener;
import pxgd.hyena.com.material.ui.util.Navigator;
import pxgd.hyena.com.material.ui.util.ThemeUtils;
import pxgd.hyena.com.material.ui.util.ToastUtils;
import pxgd.hyena.com.material.ui.view.IUserDetailView;

public class UserDetailActivity extends StatusBarActivity
        implements IUserDetailView, Toolbar.OnMenuItemClickListener  {

    private static final String EXTRA_LOGIN_NAME = "loginName";
    private static final String EXTRA_AVATAR_URL = "avatarUrl";
    private static final String NAME_IMG_AVATAR = "imgAvatar";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.tv_login_name)
    TextView tvLoginName;
    @BindView(R.id.tv_github_username)
    TextView tvGithubUsername;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    private UserDetailPagerAdapter adapter;
    private IUserDetailPresenter userDetailPresenter;
    private String loginName;
    private String githubUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        ButterKnife.bind(this);
        ViewCompat.setTransitionName(imgAvatar, NAME_IMG_AVATAR);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.user_detail);
        toolbar.setOnMenuItemClickListener(this);
        adapter = new UserDetailPagerAdapter(this, viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
        loginName = getIntent().getStringExtra(EXTRA_LOGIN_NAME);
        tvLoginName.setText(loginName);
        String avatarUrl = getIntent().getStringExtra(EXTRA_AVATAR_URL);
        if (!TextUtils.isEmpty(avatarUrl)) {
            GlideApp.with(this).load(avatarUrl).placeholder(R.drawable.image_placeholder).into(imgAvatar);
        }
        userDetailPresenter = new UserDetailPresenter(this, this);
        userDetailPresenter.getUserAsyncTask(loginName);
    }


    public static void startWithTransitionAnimation(@NonNull Activity activity,
                                                    String loginName,
                                                    @NonNull ImageView imgAvatar,
                                                    String avatarUrl)
    {
        Intent intent = new Intent(activity, UserDetailActivity.class);
        intent.putExtra(EXTRA_LOGIN_NAME, loginName);
        intent.putExtra(EXTRA_AVATAR_URL, avatarUrl);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imgAvatar, NAME_IMG_AVATAR);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
    public static void start(@NonNull Activity activity, String loginName) {
        Intent intent = new Intent(activity, UserDetailActivity.class);
        intent.putExtra(EXTRA_LOGIN_NAME, loginName);
        activity.startActivity(intent);
    }
    public static void start(@NonNull Context context, String loginName) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_LOGIN_NAME, loginName);
        context.startActivity(intent);
    }
    @OnClick(R.id.img_avatar)
    void onBtnAvatarClick() {
        userDetailPresenter.getUserAsyncTask(loginName);
    }
    @OnClick(R.id.tv_github_username)
    void onBtnGithubUsernameClick() {
        if (!TextUtils.isEmpty(githubUsername)) {
            Navigator.openInBrowser(this, "https://github.com/" + githubUsername);
        }
    }






    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_in_browser:
                Navigator.openInBrowser(this, ApiDefine.USER_LINK_URL_PREFIX + loginName);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onGetUserOk(@NonNull User user) {
        GlideApp.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.image_placeholder).into(imgAvatar);
        tvLoginName.setText(user.getLoginName());
        if (TextUtils.isEmpty(user.getGithubUsername())) {
            tvGithubUsername.setVisibility(View.INVISIBLE);
            tvGithubUsername.setText(null);
        } else {
            tvGithubUsername.setVisibility(View.VISIBLE);
            tvGithubUsername.setText(Html.fromHtml("<u>" + user.getGithubUsername() + "@github.com" + "</u>"));
        }
        tvCreateTime.setText(getString(R.string.register_time__, user.getCreateAt().toString("yyyy-MM-dd")));
        tvScore.setText(getString(R.string.score__, user.getScore()));
        adapter.setUser(user);
        githubUsername = user.getGithubUsername();
    }
    @Override
    public void onGetCollectTopicListOk(@NonNull List<Topic> topicList) {
        adapter.setCollectTopicList(topicList);
    }
    @Override
    public void onGetUserError(@NonNull String message) {
        ToastUtils.with(this).show(message);
    }
    @Override
    public void onGetUserStart() {
        progressWheel.spin();
    }
    @Override
    public void onGetUserFinish() {
        progressWheel.stopSpinning();
    }
}
