package pxgd.hyena.com.material;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import pxgd.hyena.com.material.ui.listener.NavigationFinishClickListener;
import pxgd.hyena.com.material.ui.util.ThemeUtils;
import pxgd.hyena.com.material.ui.widget.PreviewWebView;
import pxgd.hyena.com.material.util.FormatUtils;

public class MarkdownPreviewActivity extends StatusBarActivity {

    private static final String EXTRA_MARKDOWN = "markdown";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_preview)
    PreviewWebView webPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_preview);

        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        String markdown = getIntent().getStringExtra(EXTRA_MARKDOWN);
        webPreview.loadRenderedContent(FormatUtils.handleHtml(FormatUtils.renderMarkdown(markdown)).body().html());
    }

    public static void start(@NonNull Activity activity, String markdown) {
        Intent intent = new Intent(activity, MarkdownPreviewActivity.class);
        intent.putExtra(EXTRA_MARKDOWN, markdown);
        activity.startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        if (webPreview.canGoBack()) {
            webPreview.goBack();
        } else {
            super.onBackPressed();
        }
    }


}
