package pxgd.hyena.com.criminaler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 片段类
 */
public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;


    private static final String CRIME_ID = "crimeId";
    private static final int REQUEST_DATE = 0;

    public CrimeFragment() {
        // Required empty public constructor
    }
    /**
     * 实例化一个带Bundle对象的片段对象
     * @param crimeId
     * @return
     */
    public static CrimeFragment newInstance(UUID crimeId) {
        CrimeFragment frag = new CrimeFragment();
        Bundle b = new Bundle();
        b.putSerializable(CRIME_ID, crimeId);
        frag.setArguments(b);
        return frag;
    }




    /**
     * 创建片段并初始化
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化Crime对象（从Argument中获取到GUID）
        CrimeLab list=CrimeLab.get(getActivity());
        UUID crimeId = (UUID) getArguments().getSerializable(CRIME_ID);
        mCrime = list.getCrime(crimeId);
    }
    /**
     * 创建片段视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_crime, container, false);

        //查找编辑框视图并设置事件处理
        mTitleField = v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //查找按钮，设置按钮文本
        mDateButton = v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        //禁用按钮（使它不响应事件）
        //mDateButton.setEnabled(false);
        //按钮点击事件
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //显示日期设置对话框
                FragmentManager manager = getFragmentManager();

                //得到恶行记录的日期
                Date d =mCrime.getDate();
                //实现日期加一天
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.DAY_OF_MONTH, 1);
                Date d2 = c.getTime();

                Bundle args = new Bundle();
                args.putSerializable(DateFragment.ARG_DATE,d2 );

                //实例化对话框片段（设置数据参数并显示对话框）
                DateFragment dialog = new DateFragment();
                dialog.setArguments(args);

                //设置对话框片段的（回传数据）目标片段
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, "DialogDate");
            }
        });


        //查找选择框并设置事件处理
        mSolvedCheckbox = v.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mCrime.setSolved(isChecked);
                    }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        //判断反馈的结果码
        if (requestCode == REQUEST_DATE) {
            //取出意图数据，设置按钮文本
            Date date = (Date) data.getSerializableExtra(DateFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mDateButton.setText(mCrime.getDate().toString());
        }
    }





}
