package pxgd.hyena.com.ndkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String sayHello();
    public native long myFibN(long n);
    public native long myFibNI(long n);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
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


        //tv.setText(sayHello());
        //tv.setText(stringFromJNI());
    }


}
