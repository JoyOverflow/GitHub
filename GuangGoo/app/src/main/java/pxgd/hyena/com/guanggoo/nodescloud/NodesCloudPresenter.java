package pxgd.hyena.com.guanggoo.nodescloud;

import java.util.List;

import pxgd.hyena.com.guanggoo.data.NetworkTaskScheduler;
import pxgd.hyena.com.guanggoo.data.OnResponseListener;
import pxgd.hyena.com.guanggoo.data.entity.NodeCategory;
import pxgd.hyena.com.guanggoo.data.task.GetNodesCloudTask;

/**
 *
 * @author Lenovo
 * @date 2017/9/28
 */

public class NodesCloudPresenter implements NodesCloudContract.Presenter {

    private NodesCloudContract.View mView;

    public NodesCloudPresenter(NodesCloudContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getNodesCloud() {
        mView.startLoading();
        NetworkTaskScheduler.getInstance().execute(new GetNodesCloudTask(new OnResponseListener<List<NodeCategory>>() {
            @Override
            public void onSucceed(List<NodeCategory> data) {
                mView.stopLoading();
                mView.onGetNodesCloudSucceed(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onGetNodesCloudFailed(msg);
            }
        }));
    }
}
