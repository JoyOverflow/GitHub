package pxgd.hyena.com.criminaler;

import java.util.Date;
import java.util.UUID;

/**
 * Created by oupxgd on 2017/9/18.
 */

public class Crime {

    //标识ID
    private UUID mId;
    //标题
    private String mTitle;
    //发生日期
    private Date mDate;
    //是否已得到处理
    private boolean mSolved;

    /**
     * 构造函数
     */
    public Crime() {
        mId= UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
    }
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }
    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
