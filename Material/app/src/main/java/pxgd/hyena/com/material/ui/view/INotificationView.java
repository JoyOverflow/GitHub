package pxgd.hyena.com.material.ui.view;

import android.support.annotation.NonNull;

import pxgd.hyena.com.material.model.entity.Notification;

public interface INotificationView {

    void onGetMessagesOk(@NonNull Notification notification);

    void onGetMessagesFinish();

    void onMarkAllMessageReadOk();

}
