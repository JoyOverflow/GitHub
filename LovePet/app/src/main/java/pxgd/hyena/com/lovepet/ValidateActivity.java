package pxgd.hyena.com.lovepet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.lovepet.utils.StatusBarUtils;

public class ValidateActivity extends AppCompatActivity {


    @BindView(R.id.top_text)
    TextView mTopText;
    @BindView(R.id.code_nums)
    TextView mCodeNums;
    @BindView(R.id.code_getnums)
    EditText mCodeGetnums;

    private boolean isStart = false;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            if (msg.what == 0)
            {
                isStart = false;
                mCodeNums.setText("重新获取");
                mCodeNums.setClickable(true);
                return;
            }
            mCodeNums.setText(msg.what+" s");
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorPet);
        setContentView(R.layout.activity_validate);

        ButterKnife.bind(this);
        mTopText.setText("注册");
    }
    @OnClick({R.id.top_back, R.id.code_nums, R.id.code_btn, R.id.register_agree})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.top_back:
                finish();
                break;
            case R.id.code_nums:
                //点击计时按钮
                if (isStart = true)
                {
                    mCodeNums.setClickable(false);
                }
                isStart = true;
                startTime();
                break;
            case R.id.code_btn:
                //确定按钮
                startActivity(new Intent(ValidateActivity.this,InfoActivity.class));
                finish();
                break;
            case R.id.register_agree:
                //重定向到用户协议
                startActivity(new Intent(ValidateActivity.this, ProtocolActivity.class));
                break;
            default:
                break;
        }
    }

    private void startTime()
    {
        new Thread() {
            @Override
            public void run()
            {
                super.run();
                int position = 3;
                while (isStart)
                {
                    mHandler.sendEmptyMessage(position--);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }









}
