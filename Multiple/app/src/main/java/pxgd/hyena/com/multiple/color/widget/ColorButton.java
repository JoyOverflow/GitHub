package pxgd.hyena.com.multiple.color.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import pxgd.hyena.com.multiple.color.ColorUiInterface;
import pxgd.hyena.com.multiple.color.util.ViewAttributeUtil;

/**
 * Created by chengli on 15/6/8.
 */
public class ColorButton extends AppCompatButton implements ColorUiInterface {

    private int attr_background = -1;
    private int attr_textAppreance = -1;

    public ColorButton(Context context) {
        super(context);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        ViewAttributeUtil.applyTextAppearance(this, themeId, attr_textAppreance);
    }
}
