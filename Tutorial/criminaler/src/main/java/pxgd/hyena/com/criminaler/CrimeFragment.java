package pxgd.hyena.com.criminaler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * 片段类（显示行为详请）
 */
public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;


    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;


    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    //拍照后的返回码
    private static final int REQUEST_PHOTO= 2;


    private static final String CRIME_ID = "crimeId";

    public CrimeFragment() {
        // Required empty public constructor
    }





    //刷新数据的回调接口
    private Callbacks2 mCallbacks;
    public interface Callbacks2 {
        //用于重新加载行为列表
        void onCrimeUpdated(Crime crime);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks2)activity;
    }
    //将托管活动转换为接口类型变量
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks2) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
    private void updateCrime() {
        //更改行为对象数据
        CrimeLab.get(getActivity()).updateCrime(mCrime);
        //调用接口方法（重新加载行为列表）
        mCallbacks.onCrimeUpdated(mCrime);
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

        //得到图像文件的存储位置
        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime);
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

                //更新左栏
                updateCrime();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //查找按钮，设置按钮文本
        mDateButton = v.findViewById(R.id.crime_date);
        updateDate();

        //禁用按钮（使它不响应事件）
        //mDateButton.setEnabled(false);

        //按钮点击事件
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //当前片段向对话框片段传送数据（片段间的数据传递）
                DateTimeFragment dialog = DateTimeFragment.newInstance(mCrime.getDate());
                //设置回传的目标片段和请求码（以便数据回传）
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);

                //弹出对话框
                FragmentManager manager = getFragmentManager();
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

                        //更新左栏
                        updateCrime();
                    }
        });

        PackageManager packageManager = getActivity().getPackageManager();


        //拍照按钮
        mPhotoButton = v.findViewById(R.id.crime_camera);
        mPhotoView =v.findViewById(R.id.crime_photo);

        //用于拍照的隐式意图
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //是否有位置存储照片，是否有安装相机应用
        boolean canTakePhoto =
                mPhotoFile != null &&
                        captureImage.resolveActivity(packageManager) != null;

        mPhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto) {
            //要获取全尺寸照片（设置意图）
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        //触发拍照
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击相机按钮启动相机应用
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        //将位图文件载入ImageView视图中
        updatePhotoView();
        return v;
    }
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists())
            mPhotoView.setImageDrawable(null);
        else {
            //ImageView视图中显示图像
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(),
                    getActivity()
            );
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    /**
     * 接收对话框片段的返回数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DateTimeFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();

            //更新左栏
            updateCrime();

        }else if (requestCode == REQUEST_CONTACT && data != null) {

            //更新左栏
            updateCrime();
        }
        else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();

            //更新左栏
            updateCrime();
        }
    }
    //设置日期按钮的文本
    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
