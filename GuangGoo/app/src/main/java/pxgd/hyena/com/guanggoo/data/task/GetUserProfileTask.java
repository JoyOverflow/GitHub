package pxgd.hyena.com.guanggoo.data.task;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import pxgd.hyena.com.guanggoo.data.OnResponseListener;
import pxgd.hyena.com.guanggoo.data.entity.UserProfile;

/**
 *
 * @author Lenovo
 * @date 2017/9/29
 */

public class GetUserProfileTask extends BaseTask<UserProfile> {
    private String mUrl;

    public GetUserProfileTask(String url, OnResponseListener<UserProfile> listener) {
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

        Elements userProfileElements = doc.select("div.user-page .profile");
        if (userProfileElements.isEmpty()) {
            failedOnUI("获取用户资料失败");
            return;
        }

        Elements headerElements = userProfileElements.select("div.ui-header");
        if (headerElements.isEmpty()) {
            failedOnUI("获取用户资料失败");
            return;
        }

        UserProfile profile = new UserProfile();

        profile.setUrl(mUrl);
        profile.setAvatar(headerElements.select("img.avatar").attr("src"));
        profile.setUsername(headerElements.select("div.username").text());
        profile.setNumber(headerElements.select("div.user-number .number").text());
        profile.setSince(headerElements.select("div.user-number .since").text());

        Elements followStatusElements = headerElements.select("span.label-success");
        if (!followStatusElements.isEmpty()) {
            profile.setFollowed("取消关注".equals(followStatusElements.select("a").text()));
        }

        if (profile.isValid()) {
            successOnUI(profile);
        } else {
            failedOnUI("获取用户资料出错");
        }
    }
}
