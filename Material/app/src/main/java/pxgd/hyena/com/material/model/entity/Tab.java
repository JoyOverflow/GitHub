package pxgd.hyena.com.material.model.entity;

import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import pxgd.hyena.com.material.BuildConfig;
import pxgd.hyena.com.material.R;

public enum Tab {

    all(R.string.app_name),
    good(R.string.tab_good),
    unknown(R.string.tab_unknown),
    share(R.string.tab_share),
    ask(R.string.tab_ask),
    job(R.string.tab_job),
    dev(R.string.tab_dev);

    @StringRes
    private final int nameId;
    Tab(@StringRes int nameId) {
        this.nameId = nameId;
    }
    @StringRes
    public int getNameId() {
        return nameId;
    }

    public static List<Tab> getPublishableTabList() {
        List<Tab> tabList = new ArrayList<>();
        if (BuildConfig.DEBUG) {
            tabList.add(dev);
        }
        tabList.add(share);
        tabList.add(ask);
        tabList.add(job);
        return tabList;
    }

}
