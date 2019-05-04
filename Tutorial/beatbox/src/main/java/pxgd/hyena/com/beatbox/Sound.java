package pxgd.hyena.com.beatbox;

/**
 * 声音文件类
 */
public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;

    /**
     * 构造（传递文件路径）
     * @param assetPath
     */
    public Sound(String assetPath) {
        mAssetPath = assetPath;

        //分离出文件名
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];

        //删除.wav后缀名
        mName = filename.replace(".wav", "");
    }

    /**
     * 返回声音的文件路径
     * @return
     */
    public String getAssetPath() {
        return mAssetPath;
    }

    /**
     * 返回声音的文件名称（无后缀名）
     * @return
     */
    public String getName() {
        return mName;
    }


    public Integer getSoundId() {
        return mSoundId;
    }
    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
