package pxgd.hyena.com.guanggoo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import pxgd.hyena.com.guanggoo.router.FragmentFactory;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public abstract class BaseFragment<T> extends Fragment {
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";

    protected T mPresenter;
    protected FragmentCallBack mListener;

    protected String mUrl;
    protected String mTitle;

    protected FragmentFactory.PageType mPageType;

    public void setPresenter(T presenter) {
        mPresenter = presenter;
    }

    public boolean onBackPressed() {
        return false;
    }

    public void initParams() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString(KEY_URL);
            mTitle = bundle.getString(KEY_TITLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallBack) {
            mListener = (FragmentCallBack) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentCallBack");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * 用于 Activity 的标题
     * @return 标题
     */
    public abstract String getTitle();

    public String getUrl() {
        return mUrl;
    }

    public FragmentFactory.PageType getPageType() {
        return mPageType;
    }

    public void setPageType(FragmentFactory.PageType pageType) {
        this.mPageType = pageType;
    }

    public void startLoading() {
        if (mListener != null) {
            mListener.startLoading();
        }
    }

    public void stopLoading() {
        if (mListener != null) {
            mListener.stopLoading();
        }
    }

    public void toast(String msg) {
        if (getContext() == null) {
            return;
        }

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
