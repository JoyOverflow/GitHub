package pxgd.hyena.com.guanggoo.data.task;

import android.text.TextUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import pxgd.hyena.com.guanggoo.data.AuthInfoManager;
import pxgd.hyena.com.guanggoo.data.OnResponseListener;
import pxgd.hyena.com.guanggoo.util.ConstantUtil;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public class AuthCheckTask extends BaseTask<String> {
    public AuthCheckTask(OnResponseListener<String> listener) {
        super(listener);
    }

    @Override
    public void run() {
        try {
            if (!TextUtils.isEmpty(AuthInfoManager.getInstance().getUsername()) &&
                    !TextUtils.isEmpty(AuthInfoManager.getInstance().getAvatar())) {
                successOnUI("succeed");
                return;
            }

            Document doc = get(ConstantUtil.BASE_URL);
            Elements elements = doc.select("div.usercard");
            if (!elements.isEmpty()) {
                Element usercardElement = elements.first();

                AuthInfoManager.getInstance().setUsername(usercardElement.select("div.username").first().text());
                AuthInfoManager.getInstance().setAvatar(usercardElement.select("img.avatar").first().attr("src"));
                successOnUI("succeed");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        AuthInfoManager.getInstance().setUsername(null);
        AuthInfoManager.getInstance().setAvatar(null);
        failedOnUI("auth failed");
    }
}
