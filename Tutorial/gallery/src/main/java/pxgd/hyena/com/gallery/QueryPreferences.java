package pxgd.hyena.com.gallery;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * 读取和写入存储值
 */
public class QueryPreferences {

    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_LAST_RESULT_ID = "lastPicId";
    /**
     * 获取保存的字串值
     * @param context
     * @return
     */
    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }
    /**
     * 设置字串数据（异步方式）
     * @param context
     * @param query
     */
    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }
    /**
     * 获取最近的图像ID
     * @param context
     * @return
     */
    public static String getLastPicId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LAST_RESULT_ID, null);
    }
    /**
     * 设置最近的图像ID
     * @param context
     * @param lastResultId
     */
    public static void setLastPicId(Context context, String lastResultId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_RESULT_ID, lastResultId)
                .apply();
    }
}
