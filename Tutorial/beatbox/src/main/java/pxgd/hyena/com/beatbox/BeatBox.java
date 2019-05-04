package pxgd.hyena.com.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {
    //用于记录日志
    private static final String TAG = "BeatBox";
    //用于存储声音资源
    private static final String SOUNDS_FOLDER = "sounds";
    //用于访问Assets目录的管理器类
    private AssetManager mAssets;
    //目录下的声音文件集合
    private List<Sound> mSounds;


    private SoundPool mSoundPool;
    private static final int MAX_SOUNDS = 5;
    /**
     * 构造函数
     * @param context
     */
    public BeatBox(Context context) {
        mAssets = context.getAssets();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool= new SoundPool.Builder()
                    .setMaxStreams(MAX_SOUNDS)
                    .build();
        } else
            mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);


        loadSounds();
    }
    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException ioe) {
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }
        mSounds = new ArrayList<>();
        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                //预加载此音频文件
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe) {
                //红色的错误异常日志
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }
    /**
     * 返回声音文件集合
     * @return
     */
    public List<Sound> getSounds() {
        return mSounds;
    }
    /**
     * 把音频文件载入SoundPool待播
     * @param sound
     * @throws IOException
     */
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());

        //返回一个ID（int型）
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }
    /**
     * 播放音频
     * @param sound
     */
    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(
                soundId,
                1.0f,
                1.0f,
                1,
                0,
                1.0f
        );
    }
    /**
     * 释放SoundPool（音频播放完毕）
     */
    public void release() {
        mSoundPool.release();
    }
}
