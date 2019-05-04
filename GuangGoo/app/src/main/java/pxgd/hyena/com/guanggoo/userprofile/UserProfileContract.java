package pxgd.hyena.com.guanggoo.userprofile;


import pxgd.hyena.com.guanggoo.base.BasePresenter;
import pxgd.hyena.com.guanggoo.base.BaseView;
import pxgd.hyena.com.guanggoo.data.entity.UserProfile;

/**
 *
 * @author Lenovo
 * @date 2017/9/29
 */

public interface UserProfileContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取用户信息
         * @param url 用户信息网址
         */
        void getUserProfile(String url);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 获取用户信息成功
         * @param userProfile 用户信息
         */
        void onGetUserProfileSucceed(UserProfile userProfile);

        /**
         * 获取用户信息失败
         * @param msg 失败提示信息
         */
        void onGetUserProfileFailed(String msg);
    }
}
