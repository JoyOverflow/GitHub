package pxgd.hyena.com.criminaler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * 处理位图照片的类
 */
public class PictureUtils {

    /**
     * 返回屏幕大小的位图对象
     * @param path
     * @param activity
     * @return
     */
    public static Bitmap getScaledBitmap(String path, Activity activity) {
        //得到屏幕尺寸
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }


    /**
     * 加载文件为大小合适的位图对象
     * @param path
     * @param destWidth
     * @param destHeight
     * @return
     */
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight)
    {
        //首先确认文件生成Bitmap后的实际尺寸大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        //按指定尺寸来合理缩放图像文件
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight)
                inSampleSize = Math.round(srcHeight / destHeight);
            else
                inSampleSize = Math.round(srcWidth / destWidth);
        }

        //按缩放大小重新读取文件，创建Bitmap对象
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }
}
