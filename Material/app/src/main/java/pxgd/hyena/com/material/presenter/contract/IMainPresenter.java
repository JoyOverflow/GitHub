package pxgd.hyena.com.material.presenter.contract;

import android.support.annotation.NonNull;

import pxgd.hyena.com.material.model.entity.Tab;


public interface IMainPresenter {

    void switchTab(@NonNull Tab tab);

    void refreshTopicListAsyncTask();

    void loadMoreTopicListAsyncTask(int page);

    void getUserAsyncTask();

    void getMessageCountAsyncTask();

}
