package pxgd.hyena.com.criminaler;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * 抽象类（用于活动的基类）
 */
public abstract class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加载活动的通用布局
        //setContentView(R.layout.activity_common);
        //由子类自己提供布局资源ID
        setContentView(getLayoutResId());

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
    //可由子类重写，必需返回有效布局资源ID
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_common;
    }


    /**
     * 抽象方法（各活动返回各自要加载的片段对象）
     * @return
     */
    protected abstract Fragment createFragment();
}


