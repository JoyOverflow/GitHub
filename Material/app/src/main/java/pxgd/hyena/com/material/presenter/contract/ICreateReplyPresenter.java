package pxgd.hyena.com.material.presenter.contract;

import android.support.annotation.NonNull;

public interface ICreateReplyPresenter {

    void createReplyAsyncTask(@NonNull String topicId, String content, String targetId);

}
