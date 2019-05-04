package pxgd.hyena.com.material.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.material.R;
import pxgd.hyena.com.material.UserDetailActivity;
import pxgd.hyena.com.material.model.entity.Message;
import pxgd.hyena.com.material.model.glide.GlideApp;
import pxgd.hyena.com.material.ui.util.Navigator;
import pxgd.hyena.com.material.ui.widget.ContentWebView;
import pxgd.hyena.com.material.util.FormatUtils;
import pxgd.hyena.com.material.util.ResUtils;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<Message> messageList = new ArrayList<>();

    public MessageListAdapter(@NonNull Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    public void setMessageListAndNotify(@NonNull List<Message> messageList) {
        this.messageList.clear();
        this.messageList.addAll(messageList);
        notifyDataSetChanged();
    }

    public void markAllMessageReadAndNotify() {
        for (Message message : messageList) {
            message.setRead(true);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(messageList.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_avatar)
        ImageView imgAvatar;

        @BindView(R.id.tv_from)
        TextView tvFrom;

        @BindView(R.id.tv_time)
        TextView tvTime;

        @BindView(R.id.tv_action)
        TextView tvAction;

        @BindView(R.id.badge_read)
        View badgeRead;

        @BindView(R.id.web_content)
        ContentWebView webContent;

        @BindView(R.id.tv_topic_title)
        TextView tvTopicTitle;

        private Message message;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Message message) {
            this.message = message;

            GlideApp.with(activity).load(
                    message.getAuthor().getAvatarUrl()
            ).placeholder(R.drawable.image_placeholder).into(imgAvatar);

            tvFrom.setText(message.getAuthor().getLoginName());
            tvTime.setText(FormatUtils.getRelativeTimeSpanString(message.getCreateAt()));
            tvTime.setTextColor(ResUtils.getThemeAttrColor(activity, message.isRead() ? android.R.attr.textColorSecondary : R.attr.colorAccent));
            badgeRead.setVisibility(message.isRead() ? View.GONE : View.VISIBLE);
            tvTopicTitle.setText(activity.getString(R.string.topic__, message.getTopic().getTitle()));

            // 判断通知类型
            if (message.getType() == Message.Type.at) {
                if (message.getReply() == null || TextUtils.isEmpty(message.getReply().getId())) {
                    tvAction.setText(R.string.at_you_in_topic);
                    webContent.setVisibility(View.GONE);
                } else {
                    tvAction.setText(R.string.at_you_in_reply);
                    webContent.setVisibility(View.VISIBLE);
                    webContent.loadRenderedContent(message.getReply().getContentHtml());  // 这里直接使用WebView，有性能问题
                }
            } else {
                tvAction.setText(R.string.reply_your_topic);
                webContent.setVisibility(View.VISIBLE);
                webContent.loadRenderedContent(message.getReply().getContentHtml());  // 这里直接使用WebView，有性能问题
            }
        }

        @OnClick(R.id.img_avatar)
        void onBtnAvatarClick() {
            UserDetailActivity.startWithTransitionAnimation(activity, message.getAuthor().getLoginName(), imgAvatar, message.getAuthor().getAvatarUrl());
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            Navigator.TopicWithAutoCompat.start(activity, message.getTopic().getId());
        }

    }

}
