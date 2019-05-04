package pxgd.hyena.com.lovepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.lovepet.utils.StatusBarUtils;

public class RegisterActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.top_text)
    TextView mTopText;
    @BindView(R.id.register_phone)
    EditText mRegisterPhone;
    @BindView(R.id.register_pass)
    EditText mRegisterPass;
    @BindView(R.id.register_btn)
    TextView mRegisterBtn;

    public static RegisterActivity instance = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorPet);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        instance = this;

        mTopText.setText("注册");
        mRegisterPass.addTextChangedListener(this);
        mRegisterPhone.addTextChangedListener(this);
    }
    @OnClick({R.id.top_back, R.id.register_btn, R.id.parent_phone, R.id.parent_pass, R.id.register_agree})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.top_back:
                //关闭当前活动
                instance.finish();
                break;
            case R.id.register_btn:
                //点击下一步按钮
                startActivity(new Intent(instance,ValidateActivity.class));
                finish();
                break;
            case R.id.parent_phone:
                //输入手机号码（得到输入焦点）
                mRegisterPhone.requestFocus();
                break;
            case R.id.parent_pass:
                //设置登录密码（得到输入焦点）
                mRegisterPass.requestFocus();
                break;
            case R.id.register_agree:
                //点击用户协议
                startActivity(new Intent(instance, ProtocolActivity.class));
                break;
            default:
                break;
        }
    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    @Override
    public void afterTextChanged(Editable s) {
        if (!mRegisterPass.getText().toString().equals("") && !mRegisterPhone.getText().toString().equals(""))
            mRegisterBtn.setBackgroundResource(R.drawable.shape_register_btn_pet);
        else
            mRegisterBtn.setBackgroundResource(R.drawable.shape_register_btn_32);
    }
}
