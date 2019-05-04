package pxgd.hyena.com.material.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pxgd.hyena.com.material.NotificationActivity;
import pxgd.hyena.com.material.NotificationCompatActivity;
import pxgd.hyena.com.material.R;
import pxgd.hyena.com.material.TopicActivity;
import pxgd.hyena.com.material.TopicCompatActivity;
import pxgd.hyena.com.material.UserDetailActivity;
import pxgd.hyena.com.material.model.api.ApiDefine;
import pxgd.hyena.com.material.model.entity.Topic;
import pxgd.hyena.com.material.model.storage.SettingShared;
import pxgd.hyena.com.material.model.util.EntityUtils;
import pxgd.hyena.com.material.util.FormatUtils;

public final class Navigator {

    private Navigator() {}

    public static void openInMarket(@NonNull Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show(R.string.no_market_install_in_system);
        }
    }

    public static void openInBrowser(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show(R.string.no_browser_install_in_system);
        }
    }

    public static void openEmail(@NonNull Context context, @NonNull String email, @NonNull String subject, @NonNull String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:" + email));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show(R.string.no_email_client_install_in_system);
        }
    }

    public static void openShare(@NonNull Context context, @NonNull String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }

    public static boolean openStandardLink(@NonNull Context context, @Nullable String url) {
        if (FormatUtils.isUserLinkUrl(url)) {
            UserDetailActivity.start(context, Uri.parse(url).getPath().replace(ApiDefine.USER_PATH_PREFIX, ""));
            return true;
        } else if (FormatUtils.isTopicLinkUrl(url)) {
            TopicWithAutoCompat.start(context, Uri.parse(url).getPath().replace(ApiDefine.TOPIC_PATH_PREFIX, ""));
            return true;
        } else {
            return false;
        }
    }

    public static final class TopicWithAutoCompat {

        private TopicWithAutoCompat() {}

        public static final String EXTRA_TOPIC_ID = "topicId";
        public static final String EXTRA_TOPIC = "topic";

        private static Class<?> getTargetClass(@NonNull Context context) {
            return SettingShared.isEnableTopicRenderCompat(context) ? TopicCompatActivity.class : TopicActivity.class;
        }

        public static void start(@NonNull Activity activity, @NonNull Topic topic) {
            Intent intent = new Intent(activity, getTargetClass(activity));
            intent.putExtra(EXTRA_TOPIC_ID, topic.getId());
            intent.putExtra(EXTRA_TOPIC, EntityUtils.gson.toJson(topic));
            activity.startActivity(intent);
        }

        public static void start(@NonNull Activity activity, String topicId) {
            Intent intent = new Intent(activity, getTargetClass(activity));
            intent.putExtra(EXTRA_TOPIC_ID, topicId);
            activity.startActivity(intent);
        }

        public static void start(@NonNull Context context, String topicId) {
            Intent intent = new Intent(context, getTargetClass(context));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(EXTRA_TOPIC_ID, topicId);
            context.startActivity(intent);
        }

    }

    public static final class NotificationWithAutoCompat {

        private NotificationWithAutoCompat() {}

        private static Class<?> getTargetClass(@NonNull Context context) {
            return SettingShared.isEnableTopicRenderCompat(context) ? NotificationCompatActivity.class : NotificationActivity.class;
        }

        public static void start(@NonNull Activity activity) {
            activity.startActivity(new Intent(activity, getTargetClass(activity)));
        }

    }

}
