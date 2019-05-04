package pxgd.hyena.com.material;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import pxgd.hyena.com.material.ui.listener.NavigationFinishClickListener;
import pxgd.hyena.com.material.ui.util.ThemeUtils;
import pxgd.hyena.com.material.ui.util.ToastUtils;
import pxgd.hyena.com.material.util.ResUtils;

public class LicenseActivity extends StatusBarActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_license)
    TextView tvLicense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        try {
            tvLicense.setText(ResUtils.getRawString(this, R.raw.open_source));
        } catch (IOException e) {
            tvLicense.setText(null);
            ToastUtils.with(this).show("资源读取失败");
        }
    }
}
