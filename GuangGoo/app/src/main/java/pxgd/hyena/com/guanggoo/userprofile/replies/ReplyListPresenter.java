package pxgd.hyena.com.guanggoo.userprofile.replies;


import pxgd.hyena.com.guanggoo.data.NetworkTaskScheduler;
import pxgd.hyena.com.guanggoo.data.OnResponseListener;
import pxgd.hyena.com.guanggoo.data.entity.ReplyList;
import pxgd.hyena.com.guanggoo.data.task.GetReplyListTask;
import pxgd.hyena.com.guanggoo.util.UrlUtil;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public class ReplyListPresenter implements ReplyListContract.Presenter {

    private ReplyListContract.View mView;

    public ReplyListPresenter(ReplyListContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void getReplyList() {
        NetworkTaskScheduler.getInstance().execute(new GetReplyListTask(mView.getUrl(),
                new OnResponseListener<ReplyList>() {
                    @Override
                    public void onSucceed(ReplyList data) {
                        mView.onGetReplyListSucceed(data);
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onGetReplyListFailed(msg);
                    }
                }));
    }

    @Override
    public void getMoreReply(int page) {
        NetworkTaskScheduler.getInstance().execute(new GetReplyListTask(UrlUtil.appendPage(mView.getUrl(), page),
                new OnResponseListener<ReplyList>() {
                    @Override
                    public void onSucceed(ReplyList data) {
                        mView.onGetMoreReplySucceed(data);
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onGetMoreReplyFailed(msg);
                    }
                }));
    }
}
