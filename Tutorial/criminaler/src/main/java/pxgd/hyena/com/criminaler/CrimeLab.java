package pxgd.hyena.com.criminaler;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    //静态的类对象（与应用同步存在）
    private static CrimeLab sCrimeLab;
    //用来保存行为的泛型集合
    private ArrayList<Crime> mCrimes;
    private Context mContext;

    /**
     * 私有构造方法
     * @param context
     */
    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();

        //创建空集合
        mCrimes = new ArrayList<>();

        //预添加内容
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }
    /**
     * 获取单个类实例（get方法）
     * @param context
     * @return
     */
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    /**
     * 添加一项行为到集合中
     * @param c
     */
    public void addCrime(Crime c) {
        mCrimes.add(c);
    }
    /**
     * 返回集合
     * @return
     */
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    /**
     * 返回集合内的指定行为对象
     * @param id
     * @return
     */
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id))
                return crime;
        }
        return null;
    }
    /**
     * 获取图片文件
     * @param crime
     * @return
     */
    public File getPhotoFile(Crime crime) {

        //获取外部存储的文件目录
        File externalFilesDir = mContext.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
        );
        if (externalFilesDir == null)
            return null;

        return new File(externalFilesDir,crime.getPhotoFilename());
    }
    /**
     * 更改指定的行为对象
     * @param crime
     */
    public void updateCrime(Crime crime) {
        Crime tmp=this.getCrime(crime.getId());
        tmp.setTitle(crime.getTitle());
        tmp.setDate(crime.getDate());
        tmp.setSolved(crime.isSolved());
    }


}
