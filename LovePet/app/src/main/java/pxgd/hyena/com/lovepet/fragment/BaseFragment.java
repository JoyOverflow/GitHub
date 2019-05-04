package pxgd.hyena.com.lovepet.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;


public abstract class BaseFragment extends Fragment
{
    /**
     * 加载片段布局
     * @return
     */
    public abstract int getResource();

    public abstract void init(View view);//初始化组件

    public abstract void loadingDatas();

    public abstract void startdestroy();//销毁数据，释放内存

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(getActivity()).inflate(getResource(), container, false);
        init(view);
        loadingDatas();
        return view;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Fresco.getImagePipeline().pause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Fresco.getImagePipeline().resume();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        startdestroy();
        System.gc();
    }
}
