package pxgd.hyena.com.material.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.material.R;
import pxgd.hyena.com.material.model.entity.Reply;
import pxgd.hyena.com.material.model.storage.SettingShared;
import pxgd.hyena.com.material.presenter.contract.ICreateReplyPresenter;
import pxgd.hyena.com.material.presenter.implement.CreateReplyPresenter;
import pxgd.hyena.com.material.ui.holder.EditorBarViewHolder;
import pxgd.hyena.com.material.ui.util.ToastUtils;
import pxgd.hyena.com.material.ui.view.ICreateReplyView;
import pxgd.hyena.com.material.ui.view.ITopicView;

public class CreateReplyDialog extends AppCompatDialog implements ICreateReplyView {

    public static CreateReplyDialog createWithAutoTheme(@NonNull Activity activity, @NonNull String topicId, @NonNull ITopicView topicView) {
        return new CreateReplyDialog(
                activity,
                SettingShared.isEnableThemeDark(activity) ? R.style.AppDialogDark_TopicReply : R.style.AppDialogLight_TopicReply,
                topicId,
                topicView
        );
    }

    @BindView(R.id.layout_editor_bar)
    ViewGroup editorBar;

    @BindView(R.id.layout_target)
    ViewGroup layoutTarget;

    @BindView(R.id.tv_target)
    TextView tvTarget;

    @BindView(R.id.edt_content)
    EditText edtContent;

    private final String topicId;
    private final ITopicView topicView;
    private final ProgressDialog progressDialog;
    private final ICreateReplyPresenter createReplyPresenter;

    private String targetId = null;

    private CreateReplyDialog(@NonNull Activity activity, @StyleRes int theme, @NonNull String topicId, @NonNull ITopicView topicView) {
        super(activity, theme);
        setContentView(R.layout.dialog_create_reply);
        ButterKnife.bind(this);

        this.topicId = topicId;
        this.topicView = topicView;

        progressDialog = ProgressDialog.createWithAutoTheme(activity);
        progressDialog.setCancelable(false);

        new EditorBarViewHolder(activity, editorBar, edtContent); // 创建editorBar

        createReplyPresenter = new CreateReplyPresenter(activity, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    @OnClick(R.id.btn_tool_close)
    void onBtnToolCloseClick() {
        dismissWindow();
    }

    @OnClick(R.id.btn_tool_send)
    void onBtnToolSendClick() {
        createReplyPresenter.createReplyAsyncTask(topicId, edtContent.getText().toString().trim(), targetId);
    }

    @OnClick(R.id.btn_clear_target)
    void onBtnClearTargetClick() {
        targetId = null;
        layoutTarget.setVisibility(View.GONE);
    }

    @Override
    public void showWindow() {
        show();
    }

    @Override
    public void dismissWindow() {
        dismiss();
    }

    @Override
    public void onAt(@NonNull Reply target, @NonNull Integer targetPosition) {
        targetId = target.getId();
        layoutTarget.setVisibility(View.VISIBLE);
        tvTarget.setText(getContext().getString(R.string.reply___floor, targetPosition + 1));
        edtContent.getText().insert(edtContent.getSelectionEnd(), "@" + target.getAuthor().getLoginName() + " ");
        showWindow();
    }

    @Override
    public void onContentError(@NonNull String message) {
        ToastUtils.with(getContext()).show(message);
        edtContent.requestFocus();
    }

    @Override
    public void onReplyTopicOk(@NonNull Reply reply) {
        topicView.appendReplyAndUpdateViews(reply);
        dismissWindow();
        targetId = null;
        layoutTarget.setVisibility(View.GONE);
        edtContent.setText(null);
        ToastUtils.with(getContext()).show(R.string.post_success);
    }

    @Override
    public void onReplyTopicStart() {
        progressDialog.show();
    }

    @Override
    public void onReplyTopicFinish() {
        progressDialog.dismiss();
    }

}
