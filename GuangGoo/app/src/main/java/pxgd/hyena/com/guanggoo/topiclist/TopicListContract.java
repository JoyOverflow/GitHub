package pxgd.hyena.com.guanggoo.topiclist;

import pxgd.hyena.com.guanggoo.base.BasePresenter;
import pxgd.hyena.com.guanggoo.base.BaseView;
import pxgd.hyena.com.guanggoo.data.entity.TopicList;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public interface TopicListContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取主题列表
         */
        void getTopicList();

        /**
         * 获取更多主题
         * @param page 第几页
         */
        void getMoreTopic(int page);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 获取主题列表成功
         * @param topicList 主题列表对象
         */
        void onGetTopicListSucceed(TopicList topicList);

        /**
         * 获取主题列表失败
         * @param msg 失败提示信息
         */
        void onGetTopicListFailed(String msg);

        /**
         * 获取更多主题成功
         * @param topicList 主题列表对象
         */
        void onGetMoreTopicSucceed(TopicList topicList);

        /**
         * 获取更多主题失败
         * @param msg 失败提示信息
         */
        void onGetMoreTopicFailed(String msg);
    }
}
