package pxgd.hyena.com.material.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.material.R;
import pxgd.hyena.com.material.UserDetailActivity;
import pxgd.hyena.com.material.model.entity.Topic;
import pxgd.hyena.com.material.model.glide.GlideApp;
import pxgd.hyena.com.material.ui.util.Navigator;
import pxgd.hyena.com.material.util.FormatUtils;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder> {

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<Topic> topicList = new ArrayList<>();

    public TopicListAdapter(@NonNull Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    public void clearTopicListAndNotify() {
        topicList.clear();
        notifyDataSetChanged();
    }

    public void setTopicListAndNotify(@NonNull List<Topic> topicList) {
        this.topicList.clear();
        this.topicList.addAll(topicList);
        notifyDataSetChanged();
    }

    public void appendTopicListAndNotify(@NonNull List<Topic> topicList) {
        int startPosition = this.topicList.size();
        this.topicList.addAll(topicList);
        notifyItemRangeInserted(startPosition, topicList.size());
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(topicList.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctv_tab)
        CheckedTextView ctvTab;

        @BindView(R.id.tv_reply_count)
        TextView tvReplyCount;

        @BindView(R.id.tv_visit_count)
        TextView tvVisitCount;

        @BindView(R.id.tv_last_reply_time)
        TextView tvLastReplyTime;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_summary)
        TextView tvSummary;

        @BindView(R.id.img_avatar)
        ImageView imgAvatar;

        @BindView(R.id.tv_author)
        TextView tvAuthor;

        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;

        @BindView(R.id.icon_good)
        View iconGood;

        private Topic topic;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Topic topic) {
            this.topic = topic;

            ctvTab.setText(topic.isTop() ? R.string.tab_top : topic.getTab().getNameId());
            ctvTab.setChecked(topic.isTop());
            tvReplyCount.setText(String.valueOf(topic.getReplyCount()));
            tvVisitCount.setText(String.valueOf(topic.getVisitCount()));
            tvLastReplyTime.setText(FormatUtils.getRelativeTimeSpanString(topic.getLastReplyAt()));
            tvTitle.setText(topic.getTitle());
            tvSummary.setText(topic.getContentSummary());
            GlideApp.with(activity).load(topic.getAuthor().getAvatarUrl()).placeholder(R.drawable.image_placeholder).into(imgAvatar);
            tvAuthor.setText(topic.getAuthor().getLoginName());
            tvCreateTime.setText(activity.getString(R.string.create_at__, topic.getCreateAt().toString("yyyy-MM-dd HH:mm:ss")));
            iconGood.setVisibility(topic.isGood() ? View.VISIBLE : View.GONE);
        }

        @OnClick(R.id.btn_topic)
        void onBtnTopicClick() {
            Navigator.TopicWithAutoCompat.start(activity, topic);
        }

        @OnClick(R.id.btn_user)
        void onBtnUserClick() {
            UserDetailActivity.startWithTransitionAnimation(activity, topic.getAuthor().getLoginName(), imgAvatar, topic.getAuthor().getAvatarUrl());
        }

    }

}
