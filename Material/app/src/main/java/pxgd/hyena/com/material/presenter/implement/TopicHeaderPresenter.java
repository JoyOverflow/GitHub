package pxgd.hyena.com.material.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import okhttp3.Headers;
import pxgd.hyena.com.material.model.api.ApiClient;
import pxgd.hyena.com.material.model.api.DefaultCallback;
import pxgd.hyena.com.material.model.entity.Result;
import pxgd.hyena.com.material.model.storage.LoginShared;
import pxgd.hyena.com.material.presenter.contract.ITopicHeaderPresenter;
import pxgd.hyena.com.material.ui.view.ITopicHeaderView;

public class TopicHeaderPresenter implements ITopicHeaderPresenter {

    private final Activity activity;
    private final ITopicHeaderView topicHeaderView;

    public TopicHeaderPresenter(@NonNull Activity activity, @NonNull ITopicHeaderView topicHeaderView) {
        this.activity = activity;
        this.topicHeaderView = topicHeaderView;
    }

    @Override
    public void collectTopicAsyncTask(@NonNull String topicId) {
        ApiClient.service.collectTopic(
                LoginShared.getAccessToken(activity), topicId).enqueue(new DefaultCallback<Result>(activity) {
            @Override
            public boolean onResultOk(int code, Headers headers, Result result) {
                topicHeaderView.onCollectTopicOk();
                return false;
            }
        });
    }

    @Override
    public void decollectTopicAsyncTask(@NonNull String topicId) {
        ApiClient.service.decollectTopic(LoginShared.getAccessToken(activity), topicId).
                enqueue(new DefaultCallback<Result>(activity) {
            @Override
            public boolean onResultOk(int code, Headers headers, Result result) {
                topicHeaderView.onDecollectTopicOk();
                return false;
            }
        });
    }

}
