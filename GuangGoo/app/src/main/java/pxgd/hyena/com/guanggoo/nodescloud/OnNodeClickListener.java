package pxgd.hyena.com.guanggoo.nodescloud;


import pxgd.hyena.com.guanggoo.data.entity.Node;

/**
 *
 * @author mazhuang
 * @date 2017/11/18
 */

public interface OnNodeClickListener {
    /**
     * 节点被点击
     * @param node 节点
     */
    void onNodeClick(Node node);
}
