package pxgd.hyena.com.guanggoo.data.entity;

import java.util.List;

/**
 *
 * @author Lenovo
 * @date 2017/9/28
 */

public class NodeCategory {
    private String label;
    private List<Node> nodes;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
