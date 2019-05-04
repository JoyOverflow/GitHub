package pxgd.hyena.com.material.ui.view;

import android.support.annotation.NonNull;

import pxgd.hyena.com.material.model.entity.Reply;
import pxgd.hyena.com.material.model.entity.TopicWithReply;

public interface ITopicView {

    void onGetTopicOk(@NonNull TopicWithReply topic);
    void onGetTopicFinish();
    void appendReplyAndUpdateViews(@NonNull Reply reply);
}
