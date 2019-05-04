package pxgd.hyena.com.gallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * 网络连接专用类
 */
public class FlickrFetchr {

    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "ouyangjun";
    private static final String SEARCH_METHOD = "search";
    private static final String FETCH_METHOD = "select";

    /**
     * 从指定Url处返回字节数组
     * @param urlSpec
     * @return
     * @throws IOException
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": " + urlSpec);
            }
            int bytesRead;
            byte[] buffer = new byte[1024];

            //循环读取网络数据直到读取完成
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            //数据全部取出后，关闭连接
            connection.disconnect();
        }
    }
    /**
     * 将字节数组转为字串
     * @param urlSpec
     * @return
     * @throws IOException
     */
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }


    /**
     * 返回指定Url的图像泛型集合（废弃）
     * @return
     */
    public List<GalleryItem> fetchItems() {
        List<GalleryItem> items = new ArrayList<>();
        try {
            //构建请求Url（自动正确参数化）
            String url = Uri.parse("http://192.168.0.100:8081/API/Photo/")
                    .buildUpon()
                    .appendQueryParameter("id", "102459")
                    .appendQueryParameter("key", API_KEY)
                    .build().toString();

            //获得Json字串
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);

            //解析Json串为对象
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }


    /**
     * 构建URL（如果是搜索则拼接新参数）
     * @param method
     * @param query
     * @return
     */
    private String buildUrl(String method, String query) {
        Uri ENDPOINT = Uri.parse("http://192.168.0.100:8081/API/Photo/")
                .buildUpon()
                .appendQueryParameter("id", "102459")
                .appendQueryParameter("key", API_KEY)
                .build();

        Uri.Builder uriBuilder = ENDPOINT.buildUpon().appendQueryParameter("m", method);
        if (method.equals(SEARCH_METHOD)) {
            uriBuilder.appendQueryParameter("s", query);
        }
        return uriBuilder.build().toString();
    }
    /**
     * 获取图像
     * @return
     */
    public List<GalleryItem> fetchPhotos() {
        String url = buildUrl(FETCH_METHOD, null);
        return downloadGalleryItems(url);
    }
    /**
     * 搜索图像
     * @param query
     * @return
     */
    public List<GalleryItem> searchPhotos(String query) {
        String url = buildUrl(SEARCH_METHOD, query);
        return downloadGalleryItems(url);
    }
    /**
     * 返回指定Url（JSON）的图像泛型集合
     * @param url
     * @return
     */
    private List<GalleryItem> downloadGalleryItems(String url) {
        List<GalleryItem> items = new ArrayList<>();
        try {
            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return items;
    }
    /**
     * 遍历JSONObject转换成一个个GalleryItem对象并加入集合中
     * @param items
     * @param jsonBody
     * @throws IOException
     * @throws JSONException
     */
    private void parseItems(List<GalleryItem> items, JSONObject jsonBody)
            throws JSONException
    {
        //生成图片信息数组
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

        //遍历数组生成一个个GalleryItem对象并加入集合中
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);

            //创建图像类对象
            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));
            //检查图像是否有url_s链接
            if (!photoJsonObject.has("url_s")) {
                continue;
            }
            item.setUrl(photoJsonObject.getString("url_s"));

            //加入集合
            items.add(item);
        }
    }



}
