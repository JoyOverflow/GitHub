package pxgd.hyena.com.testmodule;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Runnable {

    public static final double DEFAULT_LATITUDE = 37.368830;
    public static final double DEFAULT_LONGITUDE = -122.036350;
    private Dialog splashDialog;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Location location = getLocation(context);


        //显示全屏的引导对话框
        showSplashScreen();
        //启用线程
        handler = new Handler();
        AsyncTask.execute(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissSplashScreen();
    }

    //显示引导对话框
    private void showSplashScreen() {
        splashDialog = new Dialog(this, R.style.splash_screen);
        splashDialog.setContentView(R.layout.activity_splash);
        splashDialog.setCancelable(false);
        splashDialog.show();
    }

    //关闭引导对话框
    private void dismissSplashScreen() {
        if (splashDialog != null) {
            splashDialog.dismiss();
            splashDialog = null;
        }
    }


    @Override
    public void run() {
        Location location = getLocation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭引导对话框
                dismissSplashScreen();
            }
        }, 5000);
    }

    private Location getLocation() {
        Location location = null;

        //得到系统服务的引用
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //检查应用是否拥有该权限（GPS卫星定位信息）
        int fineFlag=ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);

        //WiFi基站定位信号
        int coarseFlag= ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION);

        //如果有此权限则会返回PERMISSION_GRANTED
        if(Build.VERSION.SDK_INT >= 23
                && fineFlag!= PackageManager.PERMISSION_GRANTED
                && coarseFlag != PackageManager.PERMISSION_GRANTED)
        {

            //运行时申请权限授权（系统弹出对话框，询问用户是否给予授权）
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },1);
        }
        else{
            //已有权限
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);

            try{
                location = locationManager.getLastKnownLocation(provider);
            } catch(Exception e){ }
        }



        if (location != null) {
            return location;
        } else {
            Location defaultLocation = new Location(provider);
            defaultLocation.setLatitude(DEFAULT_LATITUDE);
            defaultLocation.setLongitude(DEFAULT_LONGITUDE);
            return defaultLocation;
        }
    }



}
