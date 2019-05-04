package pxgd.hyena.com.ndkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NdkActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }
    public native long myFibN(long n);
    public native long myFibNI(long n);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);

        TextView tv1 = findViewById(R.id.sample_text1);
        TextView tv2 = findViewById(R.id.sample_text2);

        String str;
        long start,stop,result;
        start=System.currentTimeMillis();
        result=myFibN(30);
        stop=System.currentTimeMillis();
        str=String.format("耗时为：%d msec（%d）",stop-start,result);
        tv1.setText(str);


        start=System.currentTimeMillis();
        result=myFibNI(30);
        stop=System.currentTimeMillis();
        str=String.format("耗时为：%d msec（%d）",stop-start,result);
        tv2.setText(str);
    }
}
