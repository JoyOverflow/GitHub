package pxgd.hyena.com.material.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.List;

import okhttp3.Headers;
import pxgd.hyena.com.material.model.api.ApiClient;
import pxgd.hyena.com.material.model.api.ApiDefine;
import pxgd.hyena.com.material.model.api.ForegroundCallback;
import pxgd.hyena.com.material.model.entity.Result;
import pxgd.hyena.com.material.model.entity.Tab;
import pxgd.hyena.com.material.model.entity.Topic;
import pxgd.hyena.com.material.model.entity.User;
import pxgd.hyena.com.material.model.storage.LoginShared;
import pxgd.hyena.com.material.presenter.contract.IMainPresenter;
import pxgd.hyena.com.material.ui.view.IMainView;
import pxgd.hyena.com.material.util.HandlerUtils;
import retrofit2.Call;

public class MainPresenter implements IMainPresenter {

    private static final int PAGE_LIMIT = 20;

    private final Activity activity;
    private final IMainView mainView;

    private Tab tab = Tab.all;
    private Call<Result.Data<List<Topic>>> refreshCall = null;
    private Call<Result.Data<List<Topic>>> loadMoreCall = null;

    public MainPresenter(@NonNull Activity activity, @NonNull IMainView mainView) {
        this.activity = activity;
        this.mainView = mainView;
    }

    private void cancelRefreshCall() {
        if (refreshCall != null) {
            if (!refreshCall.isCanceled()) {
                refreshCall.cancel();
            }
            refreshCall = null;
        }
    }

    private void cancelLoadMoreCall() {
        if (loadMoreCall != null) {
            if (!loadMoreCall.isCanceled()) {
                loadMoreCall.cancel();
            }
            loadMoreCall = null;
        }
    }

    @Override
    public void switchTab(@NonNull Tab tab) {
        if (this.tab != tab) {
            this.tab = tab;
            cancelRefreshCall();
            cancelLoadMoreCall();
            mainView.onSwitchTabOk(tab);
        }
    }

    @Override
    public void refreshTopicListAsyncTask() {
        if (refreshCall == null) {
            final Call<Result.Data<List<Topic>>> call = ApiClient.service.getTopicList(tab, 1, PAGE_LIMIT, ApiDefine.MD_RENDER);
            refreshCall = call;
            refreshCall.enqueue(new ForegroundCallback<Result.Data<List<Topic>>>(activity) {

                @Override
                public boolean onResultOk(int code, Headers headers, Result.Data<List<Topic>> result) {
                    handleTopicList(result.getData(), new OnHandleTopicListFinishListener() {

                        @Override
                        public void onHandleTopicListFinishListener(@NonNull List<Topic> topicList) {
                            if (call == refreshCall) {
                                cancelLoadMoreCall();
                                mainView.onRefreshTopicListOk(topicList);
                                onFinish();
                            }
                        }

                    });
                    return true;
                }

                @Override
                public boolean onResultError(int code, Headers headers, Result.Error error) {
                    mainView.onRefreshTopicListError(error.getErrorMessage());
                    return false;
                }

                @Override
                public boolean onCallException(Throwable t, Result.Error error) {
                    mainView.onRefreshTopicListError(error.getErrorMessage());
                    return false;
                }

                @Override
                public boolean onCallCancel() {
                    return true;
                }

                @Override
                public void onFinish() {
                    refreshCall = null;
                }

            });
        }
    }

    @Override
    public void loadMoreTopicListAsyncTask(int page) {
        if (loadMoreCall == null) {
            final Call<Result.Data<List<Topic>>> call = ApiClient.service.getTopicList(tab, page, PAGE_LIMIT, ApiDefine.MD_RENDER);
            loadMoreCall = call;
            loadMoreCall.enqueue(new ForegroundCallback<Result.Data<List<Topic>>>(activity) {

                @Override
                public boolean onResultOk(int code, Headers headers, Result.Data<List<Topic>> result) {
                    handleTopicList(result.getData(), new OnHandleTopicListFinishListener() {

                        @Override
                        public void onHandleTopicListFinishListener(@NonNull List<Topic> topicList) {
                            if (call == loadMoreCall) {
                                mainView.onLoadMoreTopicListOk(topicList);
                                onFinish();
                            }
                        }

                    });
                    return true;
                }

                @Override
                public boolean onResultError(int code, Headers headers, Result.Error error) {
                    mainView.onLoadMoreTopicListError(error.getErrorMessage());
                    return false;
                }

                @Override
                public boolean onCallException(Throwable t, Result.Error error) {
                    mainView.onLoadMoreTopicListError(error.getErrorMessage());
                    return false;
                }

                @Override
                public boolean onCallCancel() {
                    return true;
                }

                @Override
                public void onFinish() {
                    loadMoreCall = null;
                }

            });
        }
    }

    @Override
    public void getUserAsyncTask() {
        final String accessToken = LoginShared.getAccessToken(activity);
        if (!TextUtils.isEmpty(accessToken)) {
            ApiClient.service.getUser(LoginShared.getLoginName(activity)).enqueue(new ForegroundCallback<Result.Data<User>>(activity) {

                @Override
                public boolean onResultOk(int code, Headers headers, Result.Data<User> result) {
                    if (TextUtils.equals(accessToken, LoginShared.getAccessToken(getActivity()))) {
                        LoginShared.update(getActivity(), result.getData());
                        mainView.updateUserInfoViews();
                    }
                    return false;
                }

            });
        }
    }

    @Override
    public void getMessageCountAsyncTask() {
        final String accessToken = LoginShared.getAccessToken(activity);
        if (!TextUtils.isEmpty(accessToken)) {
            ApiClient.service.getMessageCount(accessToken).enqueue(new ForegroundCallback<Result.Data<Integer>>(activity) {

                @Override
                public boolean onResultOk(int code, Headers headers, Result.Data<Integer> result) {
                    if (TextUtils.equals(accessToken, LoginShared.getAccessToken(getActivity()))) {
                        mainView.updateMessageCountViews(result.getData());
                    }
                    return false;
                }

            });
        }
    }

    private void handleTopicList(@NonNull final List<Topic> topicList, @NonNull final OnHandleTopicListFinishListener listener) {
        if (topicList.isEmpty()) {
            listener.onHandleTopicListFinishListener(topicList);
        } else {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (Topic topic : topicList) {
                        topic.markSureHandleContent();
                    }
                    HandlerUtils.handler.post(new Runnable() {

                        @Override
                        public void run() {
                            listener.onHandleTopicListFinishListener(topicList);
                        }

                    });
                }

            }).start();
        }
    }

    private interface OnHandleTopicListFinishListener {

        void onHandleTopicListFinishListener(@NonNull List<Topic> topicList);

    }

}
