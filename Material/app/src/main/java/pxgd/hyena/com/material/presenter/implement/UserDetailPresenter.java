package pxgd.hyena.com.material.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.List;

import okhttp3.Headers;
import pxgd.hyena.com.material.R;
import pxgd.hyena.com.material.model.api.ApiClient;
import pxgd.hyena.com.material.model.api.DefaultCallback;
import pxgd.hyena.com.material.model.api.ForegroundCallback;
import pxgd.hyena.com.material.model.entity.Result;
import pxgd.hyena.com.material.model.entity.Topic;
import pxgd.hyena.com.material.model.entity.User;
import pxgd.hyena.com.material.presenter.contract.IUserDetailPresenter;
import pxgd.hyena.com.material.ui.util.ActivityUtils;
import pxgd.hyena.com.material.ui.view.IUserDetailView;
import pxgd.hyena.com.material.util.HandlerUtils;

public class UserDetailPresenter implements IUserDetailPresenter {

    private final Activity activity;
    private final IUserDetailView userDetailView;

    private boolean loading = false;

    public UserDetailPresenter(@NonNull Activity activity, @NonNull IUserDetailView userDetailView) {
        this.activity = activity;
        this.userDetailView = userDetailView;
    }

    @Override
    public void getUserAsyncTask(@NonNull String loginName) {
        if (!loading) {
            loading = true;
            userDetailView.onGetUserStart();
            ApiClient.service.getUser(loginName).enqueue(new ForegroundCallback<Result.Data<User>>(activity) {

                private long startLoadingTime = System.currentTimeMillis();
                private long getPostTime() {
                    long postTime = 1000 - (System.currentTimeMillis() - startLoadingTime);
                    if (postTime > 0) {
                        return postTime;
                    } else {
                        return 0;
                    }
                }
                @Override
                public boolean onResultOk(int code, Headers headers, final Result.Data<User> result) {
                    HandlerUtils.handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (ActivityUtils.isAlive(getActivity())) {
                                userDetailView.onGetUserOk(result.getData());
                                onFinish();
                            }
                        }

                    }, getPostTime());
                    return true;
                }

                @Override
                public boolean onResultError(final int code, Headers headers, final Result.Error error) {
                    HandlerUtils.handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (ActivityUtils.isAlive(getActivity())) {
                                userDetailView.onGetUserError(code == 404 ? error.getErrorMessage() : getActivity().getString(R.string.data_load_faild_and_click_avatar_to_reload));
                                onFinish();
                            }
                        }

                    }, getPostTime());
                    return true;
                }

                @Override
                public boolean onCallException(Throwable t, Result.Error error) {
                    HandlerUtils.handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (ActivityUtils.isAlive(getActivity())) {
                                userDetailView.onGetUserError(getActivity().getString(R.string.data_load_faild_and_click_avatar_to_reload));
                                onFinish();
                            }
                        }

                    }, getPostTime());
                    return true;
                }

                @Override
                public void onFinish() {
                    userDetailView.onGetUserFinish();
                    loading = false;
                }

            });
            ApiClient.service.getCollectTopicList(loginName).enqueue(new DefaultCallback<Result.Data<List<Topic>>>(activity) {
                @Override
                public boolean onResultOk(int code, Headers headers, Result.Data<List<Topic>> result) {
                    userDetailView.onGetCollectTopicListOk(result.getData());
                    return false;
                }
            });
        }
    }

}
