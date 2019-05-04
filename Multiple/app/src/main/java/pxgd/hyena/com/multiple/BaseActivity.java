package pxgd.hyena.com.multiple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pxgd.hyena.com.multiple.color.util.SharedPreferencesMgr;


/**
 * Created by chengli on 15/6/14.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPreferencesMgr.getInt("theme", 0) == 1) {
            setTheme(R.style.theme_2);
        } else {
            setTheme(R.style.theme_1);
        }
    }
}
