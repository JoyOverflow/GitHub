package pxgd.hyena.com.criminaler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * 显示行为列表的片段类
 */
public class CrimeListFragment extends Fragment {

    //回收视图对象
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    //记录菜单项状态的成员变量（用于联动菜单项与子标题内容）
    private boolean mSubtitleVisible;

    //设备旋转时保存实例变量的值
    private static final String SAVED_SUBTITLE = "subtitle";



    //回调接口
    private Callbacks mCallbacks;
    public interface Callbacks {
        void onCrimeSelected(Crime crime);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }
    //将托管活动转换为接口类型变量
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }




    /**
     * 构造
     */
    public CrimeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //加载片段布局文件
        View v =inflater.inflate(R.layout.fragment_crime_list, container, false);

        //查找到回收视图，并设置布局管理对象（竖直或网格）
        mCrimeRecyclerView =v.findViewById(R.id.recycler_view);
        mCrimeRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity())
        );

        //若为重建则读取（旋转前保存的）状态值
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE);
        }

        //将适配器绑定到回收视图
        updateUI();
        return v;
    }
    public void updateUI() {
        //获取一个单实例（获取犯罪行为集合）
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            //创建适配器，并将其绑定到回收视图
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else{
            //通知适配器数据已更改（使回收视图刷新所有列表项）
            mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * 旋转前保存状态值
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE, mSubtitleVisible);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //通知系统，片段会接收onCreateOptionsMenu事件
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //填充菜单资源文件
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menus, menu);

        //查找指定的菜单项（根据状态值更改菜单项文本）
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible)
            subtitleItem.setTitle(R.string.hide_subtitle);
        else
            subtitleItem.setTitle(R.string.show_subtitle);
    }
    //处理菜单项的点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:

                //创建新行为（加入集合中）
                Crime crime = new Crime();
                CrimeLab list=CrimeLab.get(getActivity());
                list.addCrime(crime);

                /*
                //启动指定活动（传送行为ID）
                Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
                intent.putExtra(CrimePagerActivity.EXTRA_CRIME_ID, crime.getId());
                startActivity(intent);
                */
                //新增记录后如果是平板设备则行为列表重新加载
                mCallbacks.onCrimeSelected(crime);

                return true;
            case R.id.menu_item_show_subtitle:

                //更改记录菜单状态的成员变量（状态值）
                mSubtitleVisible = !mSubtitleVisible;
                //重建菜单项（例：告知系统,应用的菜单项文本可能有变化）
                getActivity().invalidateOptionsMenu();
                //设置子标题具体内容
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 更新子标题的具体内容
     */
    private void updateSubtitle()
    {
        //得到恶行集合的元素数（作为子标题）
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(
                R.string.subtitle_format,
                String.valueOf(crimeCount)
        );
        if (!mSubtitleVisible) {
            subtitle = null;
        }

        //修改子标题
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    /**
     * 封装视图，实现列表项的事件处理
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView);

            //项点击事件处理
            itemView.setOnClickListener(this);

            //查找项列表的视图元素
            mTitleTextView = itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }
        //供适配器调用的绑定方法
        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }
        //列表项点击事件
        @Override
        public void onClick(View v) {
            //创建显示Intent，启动指定活动（传送CrimeID数据）
            /*
            UUID id=mCrime.getId();
            Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
            intent.putExtra(CrimePagerActivity.EXTRA_CRIME_ID, id);
            startActivity(intent);
            */
            mCallbacks.onCrimeSelected(mCrime);
        }
    }
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>
    {
        //构造函数接收一个泛型集合
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }
        //需要新视图来显示列表项时调用（指定列表项布局）
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }
        //将视图和模型绑定（调用CrimeHolder提供的方法）
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}
