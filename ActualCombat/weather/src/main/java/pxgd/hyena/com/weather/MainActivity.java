package pxgd.hyena.com.weather;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Calendar;
import java.util.Map;

public class MainActivity extends ListActivity implements Runnable {

    String[] weekdays = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};

    private TemperatureAdapter temperatureAdapter;
    private InterfaceData dataList;


    private Dialog splashDialog;
    private Handler handler;
    /*
    ImageLoader收到加载及显示图片的任务，会将它交给 ImageLoaderEngine，
    由ImageLoaderEngine分发任务到具体线程池，任务通过 Cache 及 ImageDownloader来获取图片，
    中间可能经过BitmapProcessor和ImageDecoder处理，最终转换为Bitmap，
    最后交给 BitmapDisplayer 在 ImageAware 中显示
    */
    private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //定义图片加载的配置对象
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .build();
        //实例化图片加载对象
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        //绑定列表视
        temperatureAdapter = new TemperatureAdapter(this,imageLoader);
        setListAdapter(temperatureAdapter);


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
    //显示一个全屏的引导对话框（增加对话框样式和布局）
    private void showSplashScreen() {
        splashDialog = new Dialog(this, R.style.splash_screen);
        splashDialog.setContentView(R.layout.activity_splash);
        splashDialog.setCancelable(false);
        splashDialog.show();
    }
    //关闭引导对话框
    private void dismissSplashScreen() {
        if (splashDialog!=null) {
            splashDialog.dismiss();
            splashDialog = null;
        }
    }


    /**
     * 线程中执行（实现接口方法）
     */
    @Override
    public void run() {
        //从网络中获取的天气温度数据（返回指定接口）
        dataList = new WeatherRequestData(this);
        //dataList = new WeatherLocalData(this);

        //postDelay会直接将Runnable放入到MessageQueue中，并以延时时间排序，然后Looper不断从MessageQueue中读取对象进行处理。
        //延时关闭引导对话框
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //更改适配器类（传入天气温度数据）天气列表
                temperatureAdapter.setTemperatureData(dataList);

                //设置城市名称
                ((TextView) findViewById(R.id.city)).setText(dataList.getCity());
                //设置星期几
                ((TextView) findViewById(R.id.currentDayOfWeek)).setText(
                        weekdays[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1]
                );

                //设置当前温度数据（当前温度，最高，最低，湿度）
                Map<String, String> currentConditions = dataList.getCurrentConditions();
                if (!currentConditions.isEmpty()) {
                    ((TextView) findViewById(R.id.currentTemperature)).setText(
                            currentConditions.get(WeatherLocalData.CURRENT)
                    );
                    ((TextView) findViewById(R.id.currentDewPoint)).setText(
                            currentConditions.get(WeatherLocalData.DEW_POINT)
                    );
                    ((TextView) findViewById(R.id.currentHigh)).setText(
                            currentConditions.get(WeatherLocalData.HIGH)
                    );
                    ((TextView) findViewById(R.id.currentLow)).setText(
                            currentConditions.get(WeatherLocalData.LOW)
                    );
                } else {
                    ((TextView) findViewById(R.id.currentTemperature)).setText("?");
                    ((TextView) findViewById(R.id.currentDewPoint)).setText("?");
                    ((TextView) findViewById(R.id.currentHigh)).setText("?");
                    ((TextView) findViewById(R.id.currentLow)).setText("?");
                }

                //关闭引导对话框
                dismissSplashScreen();
            }
        }, 5000);
    }
}
