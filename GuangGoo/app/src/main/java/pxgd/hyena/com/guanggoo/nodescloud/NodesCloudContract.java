package pxgd.hyena.com.guanggoo.nodescloud;


import java.util.List;

import pxgd.hyena.com.guanggoo.base.BasePresenter;
import pxgd.hyena.com.guanggoo.base.BaseView;
import pxgd.hyena.com.guanggoo.data.entity.NodeCategory;

/**
 *
 * @author Lenovo
 * @date 2017/9/28
 */

public interface NodesCloudContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取所有节点
         */
        void getNodesCloud();
    }

    interface View extends BaseView<Presenter> {
        /**
         * 获取节点成功
         * @param nodesCloud 节点分类对象
         */
        void onGetNodesCloudSucceed(List<NodeCategory> nodesCloud);

        /**
         * 获取节点失败
         * @param msg 失败提示信息
         */
        void onGetNodesCloudFailed(String msg);
    }
}
