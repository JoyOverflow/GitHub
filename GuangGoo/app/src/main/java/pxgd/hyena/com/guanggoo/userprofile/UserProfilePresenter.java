package pxgd.hyena.com.guanggoo.userprofile;

import pxgd.hyena.com.guanggoo.data.NetworkTaskScheduler;
import pxgd.hyena.com.guanggoo.data.OnResponseListener;
import pxgd.hyena.com.guanggoo.data.entity.UserProfile;
import pxgd.hyena.com.guanggoo.data.task.GetUserProfileTask;

/**
 *
 * @author Lenovo
 * @date 2017/9/29
 */

public class UserProfilePresenter implements UserProfileContract.Presenter {

    private UserProfileContract.View mView;

    public UserProfilePresenter(UserProfileContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getUserProfile(String url) {
        mView.startLoading();
        NetworkTaskScheduler.getInstance().execute(new GetUserProfileTask(url, new OnResponseListener<UserProfile>() {
            @Override
            public void onSucceed(UserProfile data) {
                mView.stopLoading();
                mView.onGetUserProfileSucceed(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onGetUserProfileFailed(msg);
            }
        }));
    }
}
