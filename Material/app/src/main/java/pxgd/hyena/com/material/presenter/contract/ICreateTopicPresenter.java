package pxgd.hyena.com.material.presenter.contract;

import android.support.annotation.NonNull;

import pxgd.hyena.com.material.model.entity.Tab;


public interface ICreateTopicPresenter {

    void createTopicAsyncTask(@NonNull Tab tab, String title, String content);

}
