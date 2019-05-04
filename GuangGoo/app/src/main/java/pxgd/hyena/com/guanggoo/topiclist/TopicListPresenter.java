package pxgd.hyena.com.guanggoo.topiclist;

import pxgd.hyena.com.guanggoo.data.NetworkTaskScheduler;
import pxgd.hyena.com.guanggoo.data.OnResponseListener;
import pxgd.hyena.com.guanggoo.data.entity.TopicList;
import pxgd.hyena.com.guanggoo.data.task.BaseTask;
import pxgd.hyena.com.guanggoo.data.task.GetTopicListTask;
import pxgd.hyena.com.guanggoo.util.UrlUtil;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public class TopicListPresenter implements TopicListContract.Presenter {

    private TopicListContract.View mView;

    private BaseTask mCurrentTask;

    public TopicListPresenter(TopicListContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void getTopicList() {
        if (mCurrentTask != null) {
            mCurrentTask.cancel();
        }

        mCurrentTask = new GetTopicListTask(mView.getUrl(),
                new OnResponseListener<TopicList>() {
                    @Override
                    public void onSucceed(TopicList data) {
                        mView.onGetTopicListSucceed(data);
                        mCurrentTask = null;
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onGetTopicListFailed(msg);
                        mCurrentTask = null;
                    }
                });

        NetworkTaskScheduler.getInstance().execute(mCurrentTask);
    }

    @Override
    public void getMoreTopic(int page) {
        if (mCurrentTask != null) {
            mCurrentTask.cancel();
        }
        mCurrentTask = new GetTopicListTask(UrlUtil.appendPage(mView.getUrl(), page),
                new OnResponseListener<TopicList>() {
                    @Override
                    public void onSucceed(TopicList data) {
                        mView.onGetMoreTopicSucceed(data);
                        mCurrentTask = null;
                    }
                    @Override
                    public void onFailed(String msg) {
                        mView.onGetMoreTopicFailed(msg);
                        mCurrentTask = null;
                    }
                });
        NetworkTaskScheduler.getInstance().execute(mCurrentTask);
    }
}
