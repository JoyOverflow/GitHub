package pxgd.hyena.com.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;

    private TextView txtQuestion;

    private int currentIndex = 0;

    //定义请求码（传给子活动，然后再由子活动回传给父活动）
    private static final int REQUEST_CODE_CHEAT = 0;
    //设备旋转前保存数据的键
    private static final String KEY_INDEX = "index";
    //用来接收子活动的回传值
    private boolean mIsCheater;

    //问题数组和答案
    private Question[] questionList = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //文本显示视图
        txtQuestion = findViewById(R.id.question_txtview);

        //查找按钮视图并设置事件
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        //下一问题（按钮）
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //下一个问题（不超过数组范围）
                currentIndex = (currentIndex + 1) % questionList.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        //查看答案（按钮）
        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //告知CheatActivity，当前问题的答案
                boolean answerIsTrue = questionList[currentIndex].isAnswer();

                //启动另一个活动（发送带数据的显示意图：有明确的接收者类实例）
                Intent i = new Intent(
                        MainActivity.this,
                        CheatActivity.class
                );
                i.putExtra(CheatActivity.EXTRA_ANSWER, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });


        //判断活动是否为重建
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        //更新当前问题的显示文本
        updateQuestion();
    }
    private void updateQuestion() {
        int question = questionList[currentIndex].getResId();
        txtQuestion.setText(question);
    }




    private void checkAnswer(boolean userAnswer) {
            //当前问题的标准答案
            boolean questionAnswer = questionList[currentIndex].isAnswer();
            int messageId;

            //首先判断是否已查看答案
            if (mIsCheater)
                messageId = R.string.judgment_toast;
            else{
                if (userAnswer == questionAnswer) {
                    messageId = R.string.correct_toast;
                } else {
                    messageId = R.string.incorrect_toast;
                }
            }

            //弹出显示结果
            Toast.makeText(
                    this, messageId, Toast.LENGTH_SHORT
            ).show();
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        //请求码是否一致
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = data.getBooleanExtra(CheatActivity.ANSWER_SHOWER, false);
        }
    }

    /**
     * 在Pause或Stop之前由系统调用
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, currentIndex);
    }



}
