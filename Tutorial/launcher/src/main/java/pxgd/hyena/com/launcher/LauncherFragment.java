package pxgd.hyena.com.launcher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 主活动要加载的片段
 */
public class LauncherFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private static final String TAG = "LauncherFragment";


    public LauncherFragment() { }
    /**
     * 返回当前片段的类实例
     * @return
     */
    public static LauncherFragment newInstance() {
        return new LauncherFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //查找回收视图（指定为线性布局）
        View v = inflater.inflate(R.layout.fragment_launcher, container, false);
        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();
        return v;
    }
    private void setupAdapter() {
        //创建隐式意图
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        //查询匹配意图的所有活动
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);

        //返回活动的总数
        Log.i(TAG, "Found " + activities.size() + " activities.");

        //排序
        Collections.sort(activities,
                new Comparator<ResolveInfo>() {
                    public int compare(ResolveInfo a, ResolveInfo b) {
                        PackageManager pm = getActivity().getPackageManager();
                        return String.CASE_INSENSITIVE_ORDER.compare(
                                a.loadLabel(pm).toString(),
                                b.loadLabel(pm).toString());
                    }
        });
        //为视图绑定适配器
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }


    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder>{

        private final List<ResolveInfo> mActivities;
        public ActivityAdapter(List<ResolveInfo> activities) {
            mActivities = activities;
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ActivityHolder(view);
        }
        //定义一个ViewHolder来显示活动的标签名（启动活动的标签名即应用名）
        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {
            //调用ViewHolder的绑定方法
            ResolveInfo resolveInfo = mActivities.get(position);
            holder.bindActivity(resolveInfo);
        }
        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }
    private class ActivityHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;
        //构造
        public ActivityHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView;
            mNameTextView.setOnClickListener(this);
        }
        //由适配器调用
        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();

            //得到应用名称
            String appName = mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
        }
        @Override
        public void onClick(View v) {

            //获得活动的包名与类名
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            String packageName=activityInfo.applicationInfo.packageName;
            String className=activityInfo.name;

            //创建一个显示意图并启动目标活动
            //发送Action_Main操作
            Intent i = new Intent(Intent.ACTION_MAIN)
                    .setClassName(packageName, className)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }






}
