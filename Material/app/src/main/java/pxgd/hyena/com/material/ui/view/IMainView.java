package pxgd.hyena.com.material.ui.view;

import android.support.annotation.NonNull;

import java.util.List;

import pxgd.hyena.com.material.model.entity.Tab;
import pxgd.hyena.com.material.model.entity.Topic;

public interface IMainView {

    void onSwitchTabOk(@NonNull Tab tab);

    void onRefreshTopicListOk(@NonNull List<Topic> topicList);

    void onRefreshTopicListError(@NonNull String message);

    void onLoadMoreTopicListOk(@NonNull List<Topic> topicList);

    void onLoadMoreTopicListError(@NonNull String message);

    void updateUserInfoViews();

    void updateMessageCountViews(int count);

}
