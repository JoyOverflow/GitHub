package pxgd.hyena.com.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private boolean currentAnswer;
    private TextView txtAnswer;
    private Button btnAnswer;

    //接收父活动发送过来的意图
    public static final String EXTRA_ANSWER = "pxgd.hyena.com.geoquiz.answer";
    //返回给父活动的意图
    public static final String ANSWER_SHOWER = "pxgd.hyena.com.geoquiz.shower";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        //接收（活动启动意图）所发送出来的数据（标准答案）
        currentAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER, false);

        //点击（显示答案按钮）显示主活动传递过来的标准答案
        txtAnswer = findViewById(R.id.answer_txtview);
        btnAnswer = findViewById(R.id.answer_button);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //显示传递过来的答案
                if (currentAnswer)
                    txtAnswer.setText(R.string.true_button);
                else
                    txtAnswer.setText(R.string.false_button);

                //传送反馈结果给父活动
                setAnswerShownResult(true);

                //得到当前的API版本（以下高于指定版本才运行）
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    //创建按钮的消失动画
                    int cx = btnAnswer.getWidth() / 2;
                    int cy = btnAnswer.getHeight() / 2;
                    float radius = btnAnswer.getWidth();
                    //创建动画（起始半径为按钮宽度，结束半径为0）
                    Animator anim = ViewAnimationUtils.createCircularReveal(
                            btnAnswer,
                            cx, cy,
                            radius,
                            0
                    );
                    anim.addListener(new AnimatorListenerAdapter() {
                        //动画结束执行
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            txtAnswer.setVisibility(View.VISIBLE);
                            btnAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    //答案显示文本可见，答案按钮不可见
                    txtAnswer.setVisibility(View.VISIBLE);
                    btnAnswer.setVisibility(View.INVISIBLE);
                }


            }
        });
    }

    /**
     * 发送意图，回传结果给父活动
     * @param isAnswerShown
     */
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent i = new Intent();
        i.putExtra(ANSWER_SHOWER, isAnswerShown);
        setResult(RESULT_OK, i);
    }






}
