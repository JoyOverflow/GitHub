package pxgd.hyena.com.actualcombat;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private Spinner operators;
    private TextView answerMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //生成日志
        Log.d(this.getClass().getSimpleName(), "onCreate()");

        //隐藏文本视图
        answerMessage=findViewById(R.id.txtAnswer);
        answerMessage.setVisibility(View.INVISIBLE);

        //填充列表框
        operators=findViewById(R.id.spinOperator);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.operators_array,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        operators.setAdapter(adapter);
    }


    /**
     * 检查答案
     * @param sender
     */
    public void onCheckAnswer(View sender)
    {
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.editAnswer).getWindowToken(), 0);
        //检查答案
        checkAnswer(sender);
    }
    public void checkAnswer(View sender) {

        //计算出实际答案
        int answer = calculateNumber(
                (EditText) findViewById(R.id.editItem1),
                (EditText) findViewById(R.id.editItem2)
        );

        //获取用户输入答案
        String givenAnswer = ((EditText) findViewById(R.id.editAnswer)).getText().toString();

        //比较实际答案和输入答案，生成反馈信息
        final String message = "The answer is:" + answer;
        if(! isNumeric(givenAnswer)) {
            showAnswer(false, "Please enter only numbers!");
        } else if(Integer.parseInt(givenAnswer) == answer) {
            showAnswer(true, message);
        } else {
            showAnswer(false, message);
        }
        hideAnswer();
    }

    /**
     * 10秒后隐藏结果（答案）显示文本视图
     */
    private void hideAnswer() {
        final Runnable hideAnswer = new Runnable() {
            @Override
            public void run() {
                //隐藏文本视图
                answerMessage.setVisibility(View.INVISIBLE);
            }
        };
        //10秒后执行代码
        answerMessage.postDelayed(hideAnswer,10 * 1000);
    }
    private void showAnswer(final boolean isCorrect, final String message) {
        if (isCorrect) {
            answerMessage.setText("Correct! " + message);
            answerMessage.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            answerMessage.setText("Incorrect! " + message);
            answerMessage.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        //文本视图可见
        answerMessage.setVisibility(View.VISIBLE);
    }

    /**
     * 计算结果的值
     * @param item1
     * @param item2
     * @return
     */
    private int calculateNumber(EditText item1, EditText item2)
    {
        int answer = 0;
        int number1 = Integer.parseInt(item1.getText().toString());
        int number2 = Integer.parseInt(item2.getText().toString());

        //返回列表框的选中位置
        int result=((Spinner) findViewById(R.id.spinOperator)).getSelectedItemPosition();
        switch(result) {
            case 0:
                answer = number1 + number2;
                break;
            case 1:
                answer = number1 - number2;
                break;
            case 2:
                answer = number1 * number2;
                break;
            case 3:
                if(number2 != 0) {
                    answer = number1 / number2;
                }
                break;
        }
        return answer;
    }
    private boolean isNumeric(String str) {
        String numbers = "1234567890";
        for(int i =0; i < str.length(); i++){
            if(!numbers.contains(str.substring(i,i+1)))
                return false;
        }
        return true;
    }






}
