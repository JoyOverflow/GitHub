package pxgd.hyena.com.material.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import okhttp3.Headers;
import pxgd.hyena.com.material.model.api.ApiClient;
import pxgd.hyena.com.material.model.api.ApiDefine;
import pxgd.hyena.com.material.model.api.DefaultCallback;
import pxgd.hyena.com.material.model.entity.Result;
import pxgd.hyena.com.material.model.entity.TopicWithReply;
import pxgd.hyena.com.material.model.storage.LoginShared;
import pxgd.hyena.com.material.presenter.contract.ITopicPresenter;
import pxgd.hyena.com.material.ui.view.ITopicView;

public class TopicPresenter implements ITopicPresenter {

    private final Activity activity;
    private final ITopicView topicView;

    public TopicPresenter(@NonNull Activity activity, @NonNull ITopicView topicView) {
        this.activity = activity;
        this.topicView = topicView;
    }

    @Override
    public void getTopicAsyncTask(@NonNull String topicId) {
        ApiClient.service.getTopic(topicId, LoginShared.getAccessToken(activity), ApiDefine.MD_RENDER).enqueue(new DefaultCallback<Result.Data<TopicWithReply>>(activity) {

            @Override
            public boolean onResultOk(int code, Headers headers, Result.Data<TopicWithReply> result) {
                topicView.onGetTopicOk(result.getData());
                return false;
            }

            @Override
            public void onFinish() {
                topicView.onGetTopicFinish();
            }

        });
    }

}
