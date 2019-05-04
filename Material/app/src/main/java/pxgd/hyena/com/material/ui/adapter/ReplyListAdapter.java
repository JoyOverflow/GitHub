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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.material.LoginActivity;
import pxgd.hyena.com.material.R;
import pxgd.hyena.com.material.UserDetailActivity;
import pxgd.hyena.com.material.model.entity.Reply;
import pxgd.hyena.com.material.model.glide.GlideApp;
import pxgd.hyena.com.material.model.storage.LoginShared;
import pxgd.hyena.com.material.presenter.contract.IReplyPresenter;
import pxgd.hyena.com.material.presenter.implement.ReplyPresenter;
import pxgd.hyena.com.material.ui.util.ToastUtils;
import pxgd.hyena.com.material.ui.view.ICreateReplyView;
import pxgd.hyena.com.material.ui.view.IReplyView;
import pxgd.hyena.com.material.ui.widget.ContentWebView;
import pxgd.hyena.com.material.util.FormatUtils;

public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.ViewHolder> implements IReplyView {

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<Reply> replyList = new ArrayList<>();
    private final Map<String, Integer> positionMap = new HashMap<>();
    private final ICreateReplyView createReplyView;
    private final IReplyPresenter replyPresenter;

    private String authorLoginName = null;

    public ReplyListAdapter(@NonNull Activity activity, @NonNull ICreateReplyView createReplyView) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.createReplyView = createReplyView;
        replyPresenter = new ReplyPresenter(activity, this);
    }

    public void setReplyListAndNotify(String authorLoginName, @NonNull List<Reply> replyList) {
        this.authorLoginName = authorLoginName;
        this.replyList.clear();
        this.replyList.addAll(replyList);
        positionMap.clear();
        for (int n = 0; n < replyList.size(); n++) {
            Reply reply = replyList.get(n);
            positionMap.put(reply.getId(), n);
        }
        notifyDataSetChanged();
    }

    public void appendReplyAndNotify(@NonNull Reply reply) {
        replyList.add(reply);
        positionMap.put(reply.getId(), replyList.size() - 1);
        notifyItemInserted(replyList.size() - 1);
    }

    @Override
    public void onUpReplyOk(@NonNull Reply reply) {
        for (int position = 0; position < replyList.size(); position++) {
            Reply replyAtPosition = replyList.get(position);
            if (TextUtils.equals(reply.getId(), replyAtPosition.getId())) {
                notifyItemChanged(position, reply);
            }
        }
    }

    @Override
    public int getItemCount() {
        return replyList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            for (Object payload : payloads) {
                if (payload instanceof Reply) { // 更新点赞状态
                    Reply reply = (Reply) payload;
                    holder.bindForUps(reply);
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_avatar)
        ImageView imgAvatar;

        @BindView(R.id.tv_login_name)
        TextView tvLoginName;

        @BindView(R.id.icon_author)
        View iconAuthor;

        @BindView(R.id.tv_index)
        TextView tvIndex;

        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;

        @BindView(R.id.btn_ups)
        TextView btnUps;

        @BindView(R.id.tv_target_position)
        TextView tvTargetPosition;

        @BindView(R.id.web_content)
        ContentWebView webContent;

        @BindView(R.id.icon_deep_line)
        View iconDeepLine;

        @BindView(R.id.icon_shadow_gap)
        View iconShadowGap;

        private Reply reply;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            reply = replyList.get(position);

            GlideApp.with(activity).load(reply.getAuthor().getAvatarUrl()).placeholder(R.drawable.image_placeholder).into(imgAvatar);
            tvLoginName.setText(reply.getAuthor().getLoginName());
            iconAuthor.setVisibility(TextUtils.equals(authorLoginName, reply.getAuthor().getLoginName()) ? View.VISIBLE : View.GONE);
            tvIndex.setText(activity.getString(R.string.__floor, position + 1));
            tvCreateTime.setText(FormatUtils.getRelativeTimeSpanString(reply.getCreateAt()));

            bindForUps(reply);

            Integer targetPosition = positionMap.get(reply.getReplyId());
            if (targetPosition == null) {
                tvTargetPosition.setVisibility(View.GONE);
            } else {
                tvTargetPosition.setVisibility(View.VISIBLE);
                tvTargetPosition.setText(activity.getString(R.string.reply___floor, targetPosition + 1));
            }

            // 这里直接使用WebView，有性能问题
            webContent.loadRenderedContent(reply.getContentHtml());

            iconDeepLine.setVisibility(position == replyList.size() - 1 ? View.GONE : View.VISIBLE);
            iconShadowGap.setVisibility(position == replyList.size() - 1 ? View.VISIBLE : View.GONE);
        }

        void bindForUps(@NonNull Reply reply) {
            btnUps.setText(String.valueOf(reply.getUpList().size()));
            btnUps.setCompoundDrawablesWithIntrinsicBounds(reply.getUpList().contains(LoginShared.getId(activity)) ? R.drawable.ic_thumb_up_theme_24dp : R.drawable.ic_thumb_up_grey600_24dp, 0, 0, 0);
        }

        @OnClick(R.id.img_avatar)
        void onBtnAvatarClick() {
            UserDetailActivity.startWithTransitionAnimation(activity, reply.getAuthor().getLoginName(), imgAvatar, reply.getAuthor().getAvatarUrl());
        }

        @OnClick(R.id.btn_ups)
        void onBtnUpsClick() {
            if (LoginActivity.checkLogin(activity)) {
                if (reply.getAuthor().getLoginName().equals(LoginShared.getLoginName(activity))) {
                    ToastUtils.with(activity).show(R.string.can_not_up_yourself_reply);
                } else {
                    replyPresenter.upReplyAsyncTask(reply);
                }
            }
        }

        @OnClick(R.id.btn_at)
        void onBtnAtClick() {
            if (LoginActivity.checkLogin(activity)) {
                createReplyView.onAt(reply, positionMap.get(reply.getId()));
            }
        }

    }

}
