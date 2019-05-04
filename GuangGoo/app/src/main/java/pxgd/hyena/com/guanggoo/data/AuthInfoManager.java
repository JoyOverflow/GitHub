package pxgd.hyena.com.guanggoo.data;

import android.text.TextUtils;

import pxgd.hyena.com.guanggoo.MyApplication;
import pxgd.hyena.com.guanggoo.util.ConstantUtil;
import pxgd.hyena.com.guanggoo.util.PrefsUtil;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public class AuthInfoManager {

    private String username;

    private String avatar;

    private AuthInfoManager() {}

    private static class InstanceHolder {
        private static AuthInfoManager sInstance = new AuthInfoManager();
    }

    public static AuthInfoManager getInstance() {
        return InstanceHolder.sInstance;
    }

    public synchronized boolean isLoginIn() {
        return !TextUtils.isEmpty(username);
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized void setUsername(String username) {
        this.username = username;
    }

    public synchronized String getAvatar() {
        return avatar;
    }

    public synchronized void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public synchronized void clearAuthInfo() {
        PrefsUtil.putString(MyApplication.getInstance(), ConstantUtil.KEY_COOKIE, "");
        PrefsUtil.putString(MyApplication.getInstance(), ConstantUtil.KEY_XSRF, "");
        username = null;
        avatar = null;
    }
}
