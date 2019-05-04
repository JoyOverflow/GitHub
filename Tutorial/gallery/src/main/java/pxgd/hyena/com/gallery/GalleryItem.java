package pxgd.hyena.com.gallery;

/**
 * 图像实体类
 */
public class GalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;

    public void setId(String id) {
        mId = id;
    }
    public void setCaption(String caption) {
        mCaption = caption;
    }
    public void setUrl(String url) {
        mUrl = url;
    }

    public String getCaption() {
        return mCaption;
    }
    public String getId() {
        return mId;
    }
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String toString() {
        return mCaption;
    }
}
