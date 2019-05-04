package pxgd.hyena.com.criminaler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * 显示行为列表的片段类
 */
public class CrimeListFragment extends Fragment {

    //回收视图对象
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

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

        //将适配器绑定到回收视图
        updateUI();
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    private void updateUI() {
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

            //查找项列表的视图元素（项布局文件在Adapter内加载）
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
            UUID id=mCrime.getId();
            //Intent intent = new Intent(getActivity(), CrimeActivity.class);

            Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
            intent.putExtra(CrimePagerActivity.EXTRA_CRIME_ID, id);
            startActivity(intent);
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
