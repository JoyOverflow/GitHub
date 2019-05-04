package pxgd.hyena.com.material;

import android.content.Intent;
import android.os.Bundle;

import pxgd.hyena.com.material.ui.util.ActivityUtils;
import pxgd.hyena.com.material.util.HandlerUtils;

public class LaunchActivity extends BaseActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        HandlerUtils.handler.postDelayed(this, 2000);
    }
    @Override
    public void run() {
        if (ActivityUtils.isAlive(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
