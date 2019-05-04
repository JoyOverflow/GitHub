package pxgd.hyena.com.guanggoo.newtopic;

import android.text.TextUtils;

import pxgd.hyena.com.guanggoo.R;
import pxgd.hyena.com.guanggoo.data.entity.Node;
import pxgd.hyena.com.guanggoo.nodescloud.NodesCloudFragment;
import pxgd.hyena.com.guanggoo.router.annotations.FinishWhenCovered;
import pxgd.hyena.com.guanggoo.util.ConstantUtil;
import pxgd.hyena.com.guanggoo.util.UrlUtil;

/**
 *
 * @author mazhuang
 * @date 2017/11/18
 */

@FinishWhenCovered
public class SelectNodeFragment extends NodesCloudFragment {
    @Override
    public void onNodeClick(Node node) {
        String nodeCode = UrlUtil.getNodeCode(node.getUrl());
        if (mListener != null && !TextUtils.isEmpty(nodeCode)) {
            mListener.openPage(String.format(ConstantUtil.NEW_TOPIC_BASE_URL, nodeCode), getString(R.string.new_topic));
        }
    }
}
