package pxgd.hyena.com.material.ui.view;

import android.support.annotation.NonNull;

import java.util.List;

import pxgd.hyena.com.material.model.entity.Topic;
import pxgd.hyena.com.material.model.entity.User;

public interface IUserDetailView {

    void onGetUserOk(@NonNull User user);

    void onGetCollectTopicListOk(@NonNull List<Topic> topicList);

    void onGetUserError(@NonNull String message);

    void onGetUserStart();

    void onGetUserFinish();

}
