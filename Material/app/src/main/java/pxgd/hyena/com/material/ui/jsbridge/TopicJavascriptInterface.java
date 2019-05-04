package pxgd.hyena.com.material.ui.jsbridge;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

import pxgd.hyena.com.material.LoginActivity;
import pxgd.hyena.com.material.R;
import pxgd.hyena.com.material.UserDetailActivity;
import pxgd.hyena.com.material.model.entity.Reply;
import pxgd.hyena.com.material.model.storage.LoginShared;
import pxgd.hyena.com.material.model.util.EntityUtils;
import pxgd.hyena.com.material.presenter.contract.IReplyPresenter;
import pxgd.hyena.com.material.presenter.contract.ITopicHeaderPresenter;
import pxgd.hyena.com.material.ui.util.ToastUtils;
import pxgd.hyena.com.material.ui.view.ICreateReplyView;
import pxgd.hyena.com.material.util.HandlerUtils;


public final class TopicJavascriptInterface {

    public static final String NAME = "topicBridge";

    private final Activity activity;
    private final ICreateReplyView createReplyView;
    private final ITopicHeaderPresenter topicHeaderPresenter;
    private final IReplyPresenter replyPresenter;

    public TopicJavascriptInterface(@NonNull Activity activity, @NonNull ICreateReplyView createReplyView, @NonNull ITopicHeaderPresenter topicHeaderPresenter, @NonNull IReplyPresenter replyPresenter) {
        this.activity = activity;
        this.createReplyView = createReplyView;
        this.topicHeaderPresenter = topicHeaderPresenter;
        this.replyPresenter = replyPresenter;
    }

    @JavascriptInterface
    public void collectTopic(String topicId) {
        if (LoginActivity.checkLogin(activity)) {
            topicHeaderPresenter.collectTopicAsyncTask(topicId);
        }
    }

    @JavascriptInterface
    public void decollectTopic(String topicId) {
        if (LoginActivity.checkLogin(activity)) {
            topicHeaderPresenter.decollectTopicAsyncTask(topicId);
        }
    }

    @JavascriptInterface
    public void upReply(String replyJson) {
        if (LoginActivity.checkLogin(activity)) {
            Reply reply = EntityUtils.gson.fromJson(replyJson, Reply.class);
            if (reply.getAuthor().getLoginName().equals(LoginShared.getLoginName(activity))) {
                ToastUtils.with(activity).show(R.string.can_not_up_yourself_reply);
            } else {
                replyPresenter.upReplyAsyncTask(reply);
            }
        }
    }

    @JavascriptInterface
    public void at(final String targetJson, final int targetPosition) {
        if (LoginActivity.checkLogin(activity)) {
            HandlerUtils.handler.post(new Runnable() {

                @Override
                public void run() {
                    Reply target = EntityUtils.gson.fromJson(targetJson, Reply.class);
                    createReplyView.onAt(target, targetPosition);
                }

            });
        }
    }

    @JavascriptInterface
    public void openUser(String loginName) {
        UserDetailActivity.start(activity, loginName);
    }

}
