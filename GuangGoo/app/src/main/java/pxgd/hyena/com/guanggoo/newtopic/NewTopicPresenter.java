package pxgd.hyena.com.guanggoo.newtopic;

import com.vdurmont.emoji.EmojiParser;

import pxgd.hyena.com.guanggoo.data.NetworkTaskScheduler;
import pxgd.hyena.com.guanggoo.data.OnResponseListener;
import pxgd.hyena.com.guanggoo.data.task.NewTopicTask;

/**
 *
 * @author mazhuang
 * @date 2017/10/10
 */

public class NewTopicPresenter implements NewTopicContract.Presenter {

    private NewTopicContract.View mView;

    public NewTopicPresenter(NewTopicContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void newTopic(String title, String content) {
        mView.startLoading();

        title = EmojiParser.parseToAliases(title);
        content = EmojiParser.parseToAliases(content);

        NetworkTaskScheduler.getInstance().execute(new NewTopicTask(mView.getUrl(),
                title,
                content,
                new OnResponseListener<String>() {
                    @Override
                    public void onSucceed(String data) {
                        mView.stopLoading();
                        mView.onNewTopicSucceed();
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.stopLoading();
                        mView.onNewTopicFailed(msg);
                    }
        }));
    }
}
