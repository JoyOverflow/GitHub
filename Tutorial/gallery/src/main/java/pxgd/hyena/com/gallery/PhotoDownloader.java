package pxgd.hyena.com.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 图片下载类
 * @param <T>
 */
public class PhotoDownloader<T> extends HandlerThread {

    private static final String TAG = "PhotoDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    private Handler mRequestHandler;
    private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<>();

    //从后台线程安排需在主线程上完成的任务
    private Handler mResponseHandler;



    private ThumbnailDownloadListener<T> mThumbnailDownloadListener;
    public interface ThumbnailDownloadListener<T> {
        void onThumbnailDownloaded(T target, Bitmap bitmap);
    }
    //片段类调用
    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener) {
        mThumbnailDownloadListener = listener;
    }





    //构造函数
    public PhotoDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }


    /**
     * 下载图片的线程函数
     * @param target（下载结果显示视图）
     * @param url（图像Url）
     */
    public void queueThumbnail( T target,String url){
        Log.i(TAG,"URL:"+url);

        if (url == null) {
            //Url无效则移出此视图
            mRequestMap.remove(target);
        } else {
            //记录在线程安全的HashMap内
            mRequestMap.put(target, url);
            //从消息队列中获取Message对象，并发送给目标Handler（由它来处理）
            //消息的属性是MESSAGE_DOWNLOAD（下载）
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                    .sendToTarget();
        }
    }

    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }


    /**
     * 在Looper首次检查消息队列前调用
     */
    @Override
    protected void onLooperPrepared() {
        //实例化RequestHandler
        //PhotoDownloader类是在主线程中运行（此时是在主线程中安排后台线程）
        mRequestHandler = new Handler() {
            //定义RequestHandler在获取到Message后需执行的任务
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    Log.i(TAG, "Got a request for URL: " + mRequestMap.get(target));
                    handleRequest(target);
                }
            }
        };
    }
    //执行下载任务
    private void handleRequest(final T target) {
        try {
            final String url = mRequestMap.get(target);
            if (url == null) {
                return;
            }
            //获取字节数组并转为位图
            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "Bitmap created");



            //Post回主线程（UI更新代码会在主线程中完成）
            mResponseHandler.post(new Runnable() {
                public void run() {
                    if (mRequestMap.get(target) != url) {
                        return;
                    }
                    //移出此视图
                    mRequestMap.remove(target);
                    //触发事件（由片段类执行事件处理）
                    mThumbnailDownloadListener.onThumbnailDownloaded(target, bitmap);
                }
            });
        } catch (IOException ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }







}
