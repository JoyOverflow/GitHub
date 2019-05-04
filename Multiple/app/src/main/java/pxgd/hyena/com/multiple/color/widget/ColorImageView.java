package pxgd.hyena.com.multiple.color.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import pxgd.hyena.com.multiple.color.ColorUiInterface;
import pxgd.hyena.com.multiple.color.util.ViewAttributeUtil;

/**
 * Created by chengli on 15/6/8.
 */
public class ColorImageView extends AppCompatImageView implements ColorUiInterface {

    private int attr_img = -1;

    public ColorImageView(Context context) {
        super(context);
    }

    public ColorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_img = ViewAttributeUtil.getSrcAttribute(attrs);
    }

    public ColorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_img = ViewAttributeUtil.getSrcAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if(attr_img != -1) {
            ViewAttributeUtil.applyImageDrawable(this, themeId, attr_img);
        }
    }
}
