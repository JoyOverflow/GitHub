package pxgd.hyena.com.guanggoo.userprofile.replies;


import pxgd.hyena.com.guanggoo.base.BasePresenter;
import pxgd.hyena.com.guanggoo.base.BaseView;
import pxgd.hyena.com.guanggoo.data.entity.ReplyList;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public interface ReplyListContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取评论列表
         */
        void getReplyList();

        /**
         * 获取更多评论
         * @param page 第几页
         */
        void getMoreReply(int page);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 获取评论列表成功
         * @param replyList 评论内容
         */
        void onGetReplyListSucceed(ReplyList replyList);

        /**
         * 获取评论列表失败
         * @param msg 失败提示信息
         */
        void onGetReplyListFailed(String msg);

        /**
         * 获取更多评论成功
         * @param replyList 评论内容
         */
        void onGetMoreReplySucceed(ReplyList replyList);

        /**
         * 获取更多评论失败
         * @param msg 失败提示信息
         */
        void onGetMoreReplyFailed(String msg);
    }
}
