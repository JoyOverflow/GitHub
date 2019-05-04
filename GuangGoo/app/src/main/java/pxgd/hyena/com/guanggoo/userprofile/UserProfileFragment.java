package pxgd.hyena.com.guanggoo.userprofile;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.guanggoo.R;
import pxgd.hyena.com.guanggoo.base.BaseFragment;
import pxgd.hyena.com.guanggoo.data.AuthInfoManager;
import pxgd.hyena.com.guanggoo.data.entity.UserProfile;
import pxgd.hyena.com.guanggoo.util.ConstantUtil;

/**
 * @author mazhuang
 */
public class UserProfileFragment extends BaseFragment<UserProfileContract.Presenter> implements UserProfileContract.View {

    private UserProfile mUserProfile;

    @BindView(R.id.avatar) ImageView mAvatarImageView;
    @BindView(R.id.username) TextView mUsernameTextView;
    @BindView(R.id.number) TextView mNumberTextView;
    @BindView(R.id.logout) TextView mLogoutTextView;
    @BindView(R.id.title_favorite) TextView mFavoriteTextView;
    @BindView(R.id.title_topic) TextView mTopicTextView;
    @BindView(R.id.title_reply) TextView mReplyTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);

        ButterKnife.bind(this, root);

        initParams();

        if (mUserProfile == null) {
            mPresenter.getUserProfile(mUrl);
        } else {
            setViewData(mUserProfile);
        }

        return root;
    }

    @Override
    public void initParams() {
        super.initParams();

        if (ConstantUtil.USER_PROFILE_SELF_FAKE_URL.equals(mUrl)) {
            // 处理登录后进入自己页面的情况
            mUrl = String.format(ConstantUtil.USER_PROFILE_BASE_URL, AuthInfoManager.getInstance().getUsername());

            mLogoutTextView.setVisibility(View.VISIBLE);
            mFavoriteTextView.setText(R.string.my_favorite);
            mTopicTextView.setText(R.string.my_topic);
            mReplyTextView.setText(R.string.my_reply);
        }
    }

    @Override
    public void onGetUserProfileSucceed(UserProfile userProfile) {
        if (getContext() == null) {
            return;
        }

        mUserProfile = userProfile;
        setViewData(mUserProfile);
    }

    private void setViewData(UserProfile userProfile) {
        mUsernameTextView.setText(userProfile.getUsername());
        Glide.with(getContext())
                .load(userProfile.getAvatar())
                .centerCrop()
                .crossFade()
                .into(mAvatarImageView);
        mNumberTextView.setText(userProfile.getNumber());
    }

    @Override
    public void onGetUserProfileFailed(String msg) {
        if (getContext() == null) {
            return;
        }

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getTitle() {
        if (!TextUtils.isEmpty(mTitle)) {
            return mTitle;
        } else {
            return getString(R.string.profile);
        }
    }

    @OnClick({R.id.user_favors, R.id.user_topics, R.id.user_replies, R.id.logout})
    public void onClick(View v) {
        if (mListener == null || mUserProfile == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.user_favors:
                mListener.openPage(String.format(ConstantUtil.USER_FAVORS_BASE_URL, mUserProfile.getUsername()),
                        mFavoriteTextView.getText().toString());
                break;

            case R.id.user_topics:
                mListener.openPage(String.format(ConstantUtil.USER_TOPICS_BASE_URL, mUserProfile.getUsername()),
                        mTopicTextView.getText().toString());
                break;

            case R.id.user_replies:
                mListener.openPage(String.format(ConstantUtil.USER_REPLIES_BASE_URL, mUserProfile.getUsername()),
                        mReplyTextView.getText().toString());
                break;

            case R.id.logout:
                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AppTheme_AlertDialog))
                        .setTitle(R.string.logout_confirm)
                        .setMessage(R.string.logout_tip_message)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.onLoginStatusChanged(false);
                                getActivity().onBackPressed();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();
                break;

            default:
                break;
        }
    }
}
