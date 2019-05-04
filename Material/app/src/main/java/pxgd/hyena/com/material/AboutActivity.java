package pxgd.hyena.com.material;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.material.ui.listener.NavigationFinishClickListener;
import pxgd.hyena.com.material.ui.util.Navigator;
import pxgd.hyena.com.material.ui.util.ThemeUtils;

public class AboutActivity extends StatusBarActivity {

    public static final String VERSION_TEXT = BuildConfig.VERSION_NAME + "-build-" + BuildConfig.VERSION_CODE;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_version)
    TextView tvVersion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        setFitsStatusBarMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        tvVersion.setText(VERSION_TEXT);
    }


    @OnClick(R.id.btn_version)
    void onBtnVersionClick() {
        // nothing to do
    }
    @OnClick(R.id.btn_open_source_url)
    void onBtnOpenSourceUrlClick() {
        Navigator.openInBrowser(this, getString(R.string.open_source_url_content));
    }
    @OnClick(R.id.btn_about_cnode)
    void onBtnAboutCNodeClick() {
        Navigator.openInBrowser(this, getString(R.string.about_cnode_content));
    }
    @OnClick(R.id.btn_about_author)
    void onBtnAboutAuthorClick() {
        Navigator.openInBrowser(this, getString(R.string.about_author_content));
    }
    @OnClick(R.id.btn_open_in_market)
    void onBtnOpenInMarketClick() {
        Navigator.openInMarket(this);
    }
    @OnClick(R.id.btn_advice_feedback)
    void onBtnAdviceFeedbackClick() {
        Navigator.openEmail(
                this,
                "takwolf@foxmail.com",
                "来自 CNodeMD-" + VERSION_TEXT + " 的客户端反馈",
                "设备信息：Android " + Build.VERSION.RELEASE + " - " + Build.MANUFACTURER + " - " + Build.MODEL + "\n（如果涉及隐私请手动删除这个内容）\n\n"
        );
    }
    @OnClick(R.id.btn_open_source_license)
    void onBtnOpenSourceLicenseClick() {
        startActivity(new Intent(this, LicenseActivity.class));
    }

}
