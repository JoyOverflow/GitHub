package pxgd.hyena.com.material;

import android.os.Bundle;

import pxgd.hyena.com.material.ui.util.Navigator;
import pxgd.hyena.com.material.ui.util.ToastUtils;

public class LinkRouterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Navigator.openStandardLink(this, getIntent().getDataString())) {
            ToastUtils.with(this).show(R.string.invalid_link);
        }
        finish();
    }
}
