package pxgd.hyena.com.lovepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.lovepet.utils.StatusBarUtils;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.top_text)
    TextView mTopText;
    public static LoginActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorPet);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        instance = this;
        mTopText.setText("登录");
    }

    @OnClick({R.id.top_back, R.id.login_miss, R.id.login_btn, R.id.login_more})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.top_back:
                finish();
                break;
            case R.id.login_miss:
                //忘记了密码
                startActivity(new Intent(instance, ForgetActivity.class));
                break;
            case R.id.login_btn:
                //登录
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }




}
