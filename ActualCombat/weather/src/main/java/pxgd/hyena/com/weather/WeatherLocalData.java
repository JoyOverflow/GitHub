package pxgd.hyena.com.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 示例虚拟数据
 */
class WeatherLocalData implements InterfaceData {

    public static final String CURRENT = "apparent";
    public static final String LOW = "minimum";
    public static final String HIGH = "maximum";
    public static final String DEW_POINT = "dew point";
    private final Context context;

    public WeatherLocalData(Context context) {
        this.context = context;
    }
    private Drawable drawable(int resId) {
        return context.getResources().getDrawable(resId);
    }

    @Override
    public List<TemperatureItem> getTemperatureItems() {
        List<TemperatureItem>items = new ArrayList<>();
        items.add(new TemperatureItem(drawable(R.drawable.early_sunny), "今天", "晴朗", "西北风 3-8 级."));
        items.add(new TemperatureItem(drawable(R.drawable.night_clear), "今晚", "晴朗", "东南风转西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.sunny_icon), "周四", "晴朗", "西风转西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.night_foggy), "周三", "雾", "西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.day_foggy), "周二", "雾", "西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.night_part_clear), "周一", "晴朗", "西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.sunny_icon), "周日", "Sunny", "西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.night_part_cloudy), "周六", "多云", "西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.snowy1), "周五", "大雪", "西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.night_part_clear), "周四", "多云", "西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.early_sunny), "周三", "晴朗", "西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.day_part_cloudy), "周二", "晴朗", "西北风."));
        items.add(new TemperatureItem(drawable(R.drawable.early_sunny), "周一", "晴朗", "西北风."));
        return items;
    }

    @Override
    public Map<String, String> getCurrentConditions() {
        Map<String, String> currentConditions = new HashMap<>();
        currentConditions.put(CURRENT,"23");
        currentConditions.put(LOW,"29");
        currentConditions.put(HIGH,"21");
        currentConditions.put(DEW_POINT,"56");
        return currentConditions;
    }
    @Override
    public CharSequence getCity() {
        return "修水";
    }
}
