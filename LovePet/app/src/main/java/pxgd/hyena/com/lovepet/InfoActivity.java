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

public class InfoActivity extends AppCompatActivity {

    @BindView(R.id.top_text)
    TextView mTopText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorPet);
        setContentView(R.layout.activity_info);

        ButterKnife.bind(this);
        mTopText.setText("添加宠物");
    }

    @OnClick({R.id.top_back, R.id.msg_long, R.id.msg_save})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.top_back:
                finish();
                break;
            case R.id.msg_long:
                //以后添加
                startActivity(new Intent(InfoActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.msg_save:
                //保存
                startActivity(new Intent(InfoActivity.this,MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
}
