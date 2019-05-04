package pxgd.hyena.com.criminaler;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends DialogFragment {

    public static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "pxgd.hyena.com.criminaler.date";
    //日期选择控件
    private DatePicker mDatePicker;
    public DateFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //对话框片段接收详情片段传送过来的日期数据（片段实例化时就已得到此数据）
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        //标题与按钮间的视图
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        //得到系统当前日期（年月日）
        Calendar calendar = Calendar.getInstance();

        //用指定日期设置当前日期
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //查找到日期选择框控件，并将其初始化
        mDatePicker =v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);


        //返回创建的对话框实例
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        //目表片段不能为null
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        //调用目标片段类的方法
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
