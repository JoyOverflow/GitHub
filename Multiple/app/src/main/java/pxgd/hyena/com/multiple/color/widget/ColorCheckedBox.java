package pxgd.hyena.com.multiple.color.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;

import pxgd.hyena.com.multiple.color.ColorUiInterface;
import pxgd.hyena.com.multiple.color.util.ViewAttributeUtil;

/**
 * Created by chengli on 15/6/8.
 */
public class ColorCheckedBox extends AppCompatCheckBox implements ColorUiInterface {

    private int attr_background = -1;

    public ColorCheckedBox(Context context) {
        super(context);
    }

    public ColorCheckedBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public ColorCheckedBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if(attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
    }
}
