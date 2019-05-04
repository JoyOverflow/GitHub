package pxgd.hyena.com.weather;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pxgd.hyena.com.weatherparse.MyParse;
import pxgd.hyena.com.weatherrequest.National;

class WeatherRequestData implements InterfaceData {

    //缺省位置（Latitude=纬度，Longitude=经度）
    //public static final double DEFAULT_LATITUDE = 29.037548;
    //public static final double DEFAULT_LONGITUDE = 114.570512;

    public static final double DEFAULT_LATITUDE = 37.368830;
    public static final double DEFAULT_LONGITUDE = -122.036350;

    private final MyParse weatherParser;
    private final Context context;

    public WeatherRequestData(Context context) {
        this.context = context;

        //得到当前经纬度，（通过依赖模块）获取天气温度数据
        Location location = getLocation(context);
        String weatherXml = new National(location).getWeatherXml();

        //（通过依赖模块）解析数据
        weatherParser = new MyParse();
        String validXml = asValidXml(weatherXml);
        try {
            weatherParser.parse(new StringReader(validXml));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String asValidXml(String weatherXml) {
        return weatherXml.replaceAll("<br>","<br/>");
    }

    @Override
    public List<TemperatureItem> getTemperatureItems() {
        ArrayList<TemperatureItem> temperatureItems = new ArrayList<TemperatureItem>();
        List<Map<String, String>> forecast = weatherParser.getLastForecast();
        if (forecast!=null) {
            for(Map<String,String> eachEntry : forecast) {
                temperatureItems.add(new TemperatureItem(
                        context.getResources().getDrawable(R.drawable.progress_small),
                        eachEntry.get("iconLink"),
                        eachEntry.get("day"),
                        eachEntry.get("shortDescription"),
                        eachEntry.get("description")
                ));
            }
        }
        return temperatureItems;
    }

    @Override
    public Map<String, String> getCurrentConditions() {
        return weatherParser.getCurrentConditions();
    }

    @Override
    public CharSequence getCity() {
        return weatherParser.getLocation();
    }

    private Location getLocation(Context context) {

//        Location location =null;
//        int fine_Location=checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION);
//        int coarse_Location=checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION);
//        if ( Build.VERSION.SDK_INT >= 23 &&
//                fine_Location != PackageManager.PERMISSION_GRANTED &&
//                coarse_Location != PackageManager.PERMISSION_GRANTED) {
//            return null;
//        }
//        Criteria criteria = new Criteria();
//        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        String provider = locationManager.getBestProvider(criteria, false);
//
//        //Location location = locationManager.getLastKnownLocation(provider);
//        try{
//            location = locationManager.getLastKnownLocation(provider);
//        } catch(Exception e){ }


        Location location =null;
        if (location != null) {
            return location;
        } else {
            Location defaultLocation = new Location(LocationManager.GPS_PROVIDER);
            defaultLocation.setLatitude(DEFAULT_LATITUDE);
            defaultLocation.setLongitude(DEFAULT_LONGITUDE);
            return defaultLocation;
        }
    }
}
