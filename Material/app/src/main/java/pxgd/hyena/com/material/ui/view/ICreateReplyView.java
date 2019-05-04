package pxgd.hyena.com.material.ui.view;

import android.support.annotation.NonNull;

import pxgd.hyena.com.material.model.entity.Reply;


public interface ICreateReplyView {

    void showWindow();

    void dismissWindow();

    void onAt(@NonNull Reply target, @NonNull Integer targetPosition);

    void onContentError(@NonNull String message);

    void onReplyTopicOk(@NonNull Reply reply);

    void onReplyTopicStart();

    void onReplyTopicFinish();

}
