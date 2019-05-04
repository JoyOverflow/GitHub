package pxgd.hyena.com.knownlocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST_CODE = 100;
    private MyLocationManager myLocationManager;
    private TextView mLocationTxtView;
    private Button mLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocationManager = new MyLocationManager(this);

        //查找视图元素，设置按钮事件处理
        mLocationTxtView = findViewById(R.id.location_txtview);
        mLocationButton = findViewById(R.id.location_button);
        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查指定权限，若无则提出申请，有则执行操作
                if (!checkPermission())
                    requestPermission();
                else
                    requestLocation();
            }
        });
    }
    //检查是否有指定权限
    private boolean checkPermission() {

        //是否允许访问GPS
        int fineFlag=ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);

        //是否允许WiFi基站定位
        int coarseFlag= ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION);

        return ((fineFlag == PackageManager.PERMISSION_GRANTED)
                &&(coarseFlag == PackageManager.PERMISSION_GRANTED));

    }
    //弹出对话框，申请指定权限（SDK小于23的应用已默认授予了权限）
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_REQUEST_CODE
            );
        }
    }
    //取经玮度数据，显示在文本视图内
    private void requestLocation() {
        //获取位置信息对象
        Location location = myLocationManager.getLastKnownLocation();
        if (location != null) {
            mLocationTxtView.setText(
                    String.format("纬度：%f，经度：%f", location.getLatitude(), location.getLongitude())
            );
        }
        else
           mLocationTxtView.setText(String.valueOf(location));
    }


    /**
     * 系统弹出请求权限的对话框后，用户选择允许或拒绝都会回调此方法
     * （授权对话框后的回调方法）
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //根据requestCode和grantResults(授权结果)做出相应处理
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户已同意
                requestLocation();
            }
        }
    }
}
