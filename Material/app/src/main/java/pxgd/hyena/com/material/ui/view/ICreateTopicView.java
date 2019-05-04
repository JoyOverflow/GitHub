package pxgd.hyena.com.material.ui.view;

import android.support.annotation.NonNull;

public interface ICreateTopicView {

    void onTitleError(@NonNull String message);

    void onContentError(@NonNull String message);

    void onCreateTopicOk(@NonNull String topicId);

    void onCreateTopicStart();

    void onCreateTopicFinish();

}
