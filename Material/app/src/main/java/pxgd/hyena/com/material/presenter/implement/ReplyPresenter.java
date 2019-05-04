package pxgd.hyena.com.material.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import okhttp3.Headers;
import pxgd.hyena.com.material.model.api.ApiClient;
import pxgd.hyena.com.material.model.api.DefaultCallback;
import pxgd.hyena.com.material.model.entity.Reply;
import pxgd.hyena.com.material.model.entity.Result;
import pxgd.hyena.com.material.model.storage.LoginShared;
import pxgd.hyena.com.material.presenter.contract.IReplyPresenter;
import pxgd.hyena.com.material.ui.view.IReplyView;

public class ReplyPresenter implements IReplyPresenter {

    private final Activity activity;
    private final IReplyView replyView;

    public ReplyPresenter(@NonNull Activity activity, @NonNull IReplyView replyView) {
        this.activity = activity;
        this.replyView = replyView;
    }

    @Override
    public void upReplyAsyncTask(@NonNull final Reply reply) {
        ApiClient.service.upReply(reply.getId(), LoginShared.getAccessToken(activity)).enqueue(new DefaultCallback<Result.UpReply>(activity) {

            @Override
            public boolean onResultOk(int code, Headers headers, Result.UpReply result) {
                if (result.getAction() == Reply.UpAction.up) {
                    reply.getUpList().add(LoginShared.getId(getActivity()));
                } else if (result.getAction() == Reply.UpAction.down) {
                    reply.getUpList().remove(LoginShared.getId(getActivity()));
                }
                replyView.onUpReplyOk(reply);
                return false;
            }

        });
    }

}
