package pxgd.hyena.com.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class MyActivity extends AppCompatActivity {

    //抽象方法（各活动返回各自要加载的片段对象）
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //加载通用活动的布局资源ID
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取片段管理器,并查找指定片段实例（以帧布局ID标识）
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.Container);
        //未找到则新创建并加入进片段管理器
        if (fragment == null) {
            //返回各活动的特定片段对象
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.Container, fragment)
                    .commit();
        }
    }











}
