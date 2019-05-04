package pxgd.hyena.com.weather;

import java.util.List;
import java.util.Map;

/**
 * Created by Clifton
 * Copyright 8/28/2014.
 */
public interface InterfaceData {
    List<TemperatureItem> getTemperatureItems();
    Map<String, String> getCurrentConditions();
    CharSequence getCity();
}
