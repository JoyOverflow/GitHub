package pxgd.hyena.com.launcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class MyActivity extends AppCompatActivity {

    /**
     * 抽象方法（各活动返回各自要加载的片段对象）
     * @return
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加载活动的通用布局
        setContentView(R.layout.activity_common);

        //在片段管理器内查找指定的片段（以帧布局ID标识）
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        //未找到则新创建并加入进片段管理器
        if (fragment == null) {
            //返回各活动的特定片段对象
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

}