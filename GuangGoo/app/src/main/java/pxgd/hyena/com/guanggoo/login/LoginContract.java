package pxgd.hyena.com.guanggoo.login;


import pxgd.hyena.com.guanggoo.base.BasePresenter;
import pxgd.hyena.com.guanggoo.base.BaseView;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public interface LoginContract {
    interface Presenter extends BasePresenter {
        /**
         * 登录
         * @param email 邮箱
         * @param password 密码
         */
        void login(String email, String password);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 登录成功
         * @param data 登录成功带回的数据，目前暂无
         */
        void onLoginSucceed(String data);

        /**
         * 登录失败
         * @param msg 失败提示信息
         */
        void onLoginFailed(String msg);
    }
}
