package pxgd.hyena.com.criminaler;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 封装对话框的片段类
 */
public class DateTimeFragment extends DialogFragment {

    private static final String ARG_DATE = "date";

    public static final String EXTRA_DATE = "pxgd.hyena.com.criminaler.date";
    //日期选择控件
    private DatePicker mDatePicker;


    //构造
    public DateTimeFragment() {}


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //接收传送过来的日期数据（片段实例化时就已得到此数据）
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        //得到当前日期（年月日）
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //填充对话框布局（内含日期选择控件）
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        //查找到日期选择框控件，并将其初始化
        mDatePicker =v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        //返回创建的对话框实例（setView设置对话框显示内容）
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            //点击对话框按钮时运行
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //得到选中的年月日
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();

                                //返回结果
                                Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                .create();
    }

    /**
     * 回传数据
     * @param resultCode
     * @param date
     */
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        //将选中日期数据附加
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        //直接调用目标片段的onActivityResult方法
        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                resultCode,
                intent
        );
    }

    /**
     * 返回当前片段实例
     * @param date
     * @return
     */
    public static DateTimeFragment newInstance(Date date) {

        //传入特定日期（接收CrimeFragment传送过来的数据）
        Bundle b = new Bundle();
        b.putSerializable(ARG_DATE, date);

        //返回指定片段类实例
        DateTimeFragment fragment = new DateTimeFragment();
        fragment.setArguments(b);
        return fragment;
    }

}
