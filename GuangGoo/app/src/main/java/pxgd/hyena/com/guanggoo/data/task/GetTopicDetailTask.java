package pxgd.hyena.com.guanggoo.data.task;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

import pxgd.hyena.com.guanggoo.data.OnResponseListener;
import pxgd.hyena.com.guanggoo.data.entity.Comment;
import pxgd.hyena.com.guanggoo.data.entity.Favorite;
import pxgd.hyena.com.guanggoo.data.entity.Topic;
import pxgd.hyena.com.guanggoo.data.entity.TopicDetail;
import pxgd.hyena.com.guanggoo.data.entity.UserProfile;


/**
 *
 * @author mazhuang
 * @date 2017/9/17
 */

public class GetTopicDetailTask extends BaseTask<TopicDetail> {

    private String mUrl;

    public GetTopicDetailTask(String url, OnResponseListener<TopicDetail> listener) {
        super(listener);
        mUrl = url;
    }

    @Override
    public void run() {
        Document doc;

        try {
            doc = get(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
            failedOnUI(e.getMessage());
            return;
        }

        Elements topicDetailElements = doc.select("div.topic-detail");

        if (topicDetailElements.isEmpty()) {
            failedOnUI("找不到主题详情");
            return;
        }

        Elements elements = topicDetailElements.select("div.ui-header");

        if (elements.isEmpty()) {
            failedOnUI("找不到主题元信息");
            return;
        }

        final TopicDetail topicDetail = new TopicDetail();

        final Topic topic = GetTopicListTask.createTopicFromElement(elements.first());

        topicDetail.setTopic(topic);
        // 解析收藏
        Favorite favorite = new Favorite();
        Elements favouriteElement = doc.select(".J_topicFavorite");
        if(favouriteElement!=null){
            String dataType = favouriteElement.attr("data-type");
            favorite.setFavorite(!Favorite.TYPE_NOT_FAVORITE.equals(dataType));
        }
        topicDetail.setFavorite(favorite);

        elements = topicDetailElements.select("div.ui-content");

        if (elements.isEmpty()) {
            failedOnUI("找不到主题内容");
            return;
        }

        topicDetail.setContent(elements.first().outerHtml());

        Elements commentsElements = doc.select("div.topic-reply");

        Map<Integer, Comment> comments = GetCommentsTask.getCommentsFromElements(commentsElements);

        topicDetail.setComments(comments);

        new GetUserProfileTask(topicDetail.getTopic().getMeta().getAuthor().getUrl(), new OnResponseListener<UserProfile>() {
            @Override
            public void onSucceed(UserProfile data) {
                if (data.isFollowed()) {
                    topicDetail.getTopic().getMeta().getAuthor().setFollowed(true);
                }
                successOnUI(topicDetail);
            }

            @Override
            public void onFailed(String msg) {
                successOnUI(topicDetail);
            }
        }).run();
    }
}
