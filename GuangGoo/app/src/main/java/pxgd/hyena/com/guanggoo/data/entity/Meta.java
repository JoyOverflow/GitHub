package pxgd.hyena.com.guanggoo.data.entity;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public class Meta {
    private Node node;
    private User author;
    private String lastTouched;
    private String createdTime;
    private User lastReplyUser;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getLastTouched() {
        return lastTouched;
    }

    public void setLastTouched(String lastTouched) {
        this.lastTouched = lastTouched;
    }

    public User getLastReplyUser() {
        return lastReplyUser;
    }

    public void setLastReplyUser(User lastReplyUser) {
        this.lastReplyUser = lastReplyUser;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
