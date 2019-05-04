package pxgd.hyena.com.material;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.material.model.api.ApiDefine;
import pxgd.hyena.com.material.model.entity.Reply;
import pxgd.hyena.com.material.model.entity.Topic;
import pxgd.hyena.com.material.model.entity.TopicWithReply;
import pxgd.hyena.com.material.model.storage.LoginShared;
import pxgd.hyena.com.material.presenter.contract.IReplyPresenter;
import pxgd.hyena.com.material.presenter.contract.ITopicHeaderPresenter;
import pxgd.hyena.com.material.presenter.contract.ITopicPresenter;
import pxgd.hyena.com.material.presenter.implement.ReplyPresenter;
import pxgd.hyena.com.material.presenter.implement.TopicHeaderPresenter;
import pxgd.hyena.com.material.presenter.implement.TopicPresenter;
import pxgd.hyena.com.material.ui.dialog.CreateReplyDialog;
import pxgd.hyena.com.material.ui.jsbridge.TopicJavascriptInterface;
import pxgd.hyena.com.material.ui.listener.DoubleClickBackToContentTopListener;
import pxgd.hyena.com.material.ui.listener.FloatingActionButtonBehaviorListener;
import pxgd.hyena.com.material.ui.listener.NavigationFinishClickListener;
import pxgd.hyena.com.material.ui.util.Navigator;
import pxgd.hyena.com.material.ui.util.ThemeUtils;
import pxgd.hyena.com.material.ui.view.IBackToContentTopView;
import pxgd.hyena.com.material.ui.view.ICreateReplyView;
import pxgd.hyena.com.material.ui.view.IReplyView;
import pxgd.hyena.com.material.ui.view.ITopicHeaderView;
import pxgd.hyena.com.material.ui.view.ITopicView;
import pxgd.hyena.com.material.ui.widget.TopicWebView;

public class TopicCompatActivity extends StatusBarActivity
        implements ITopicView, ITopicHeaderView, IReplyView, IBackToContentTopView,
        SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.web_topic)
    TopicWebView webTopic;
    @BindView(R.id.fab_reply)
    FloatingActionButton fabReply;

    private String topicId;
    private Topic topic;
    private ICreateReplyView createReplyView;
    private ITopicPresenter topicPresenter;
    private ITopicHeaderPresenter topicHeaderPresenter;
    private IReplyPresenter replyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_compat);


        ButterKnife.bind(this);
        topicId = getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC_ID);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.topic);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(this));
        topicPresenter = new TopicPresenter(this, this);
        topicHeaderPresenter = new TopicHeaderPresenter(this, this);
        replyPresenter = new ReplyPresenter(this, this);
        createReplyView = CreateReplyDialog.createWithAutoTheme(this, topicId, this);
        webTopic.addOnScrollListener(new FloatingActionButtonBehaviorListener.ForWebView(fabReply));
        webTopic.setBridgeAndLoadPage(new TopicJavascriptInterface(this, createReplyView, topicHeaderPresenter, replyPresenter));
        refreshLayout.setColorSchemeResources(R.color.color_accent);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }
    @OnClick(R.id.fab_reply)
    void onBtnReplyClick() {
        if (topic != null && LoginActivity.checkLogin(this)) {
            createReplyView.showWindow();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.REQUEST_DEFAULT && resultCode == RESULT_OK) {
            refreshLayout.setRefreshing(true);
            onRefresh();
        }
    }




    @Override
    public void onRefresh() {
        topicPresenter.getTopicAsyncTask(topicId);
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (topic != null) {
                    Navigator.openShare(this,
                            "《" + topic.getTitle() + "》\n" +
                                    ApiDefine.TOPIC_LINK_URL_PREFIX + topicId + "\n—— 来自CNode社区"
                    );
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void backToContentTop() {
        webTopic.scrollTo(0, 0);
    }
    @Override
    public void onUpReplyOk(@NonNull Reply reply) {
        webTopic.updateReply(reply);
    }
    @Override
    public void onCollectTopicOk() {
        webTopic.updateTopicCollect(true);
    }
    @Override
    public void onDecollectTopicOk() {
        webTopic.updateTopicCollect(false);
    }
    @Override
    public void onGetTopicOk(@NonNull TopicWithReply topic) {
        this.topic = topic;
        webTopic.updateTopicAndUserId(topic, LoginShared.getId(this));
    }
    @Override
    public void onGetTopicFinish() {
        refreshLayout.setRefreshing(false);
    }
    @Override
    public void appendReplyAndUpdateViews(@NonNull Reply reply) {
        webTopic.appendReply(reply);
    }
}
