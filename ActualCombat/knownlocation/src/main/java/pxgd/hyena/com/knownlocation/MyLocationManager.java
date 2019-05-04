package pxgd.hyena.com.knownlocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Ashwin on 3/18/2018.
 */

public class MyLocationManager {
    private final Context context;
    private LocationManager mLocationManager;

    public MyLocationManager(Context context) {
        this.context = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getLastKnownLocation()
    {
        //检查应用是否拥有该权限（GPS卫星定位信息）
        int fineFlag= ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION);

        //WiFi基站定位信号
        int coarseFlag= ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION);

        Location location = null;
        if(fineFlag== PackageManager.PERMISSION_GRANTED &&
                coarseFlag == PackageManager.PERMISSION_GRANTED)
        {
            //获取最后一个缓存的位置信息（当gps获取为null时，换用wifi定位来获取）
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        return location;
    }
}
