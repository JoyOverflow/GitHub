package pxgd.hyena.com.material.model.api;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import okhttp3.Headers;
import pxgd.hyena.com.material.LoginActivity;
import pxgd.hyena.com.material.R;
import pxgd.hyena.com.material.model.entity.Result;
import pxgd.hyena.com.material.ui.dialog.AlertDialogUtils;
import pxgd.hyena.com.material.ui.util.ToastUtils;

public class DefaultCallback<T extends Result> extends ForegroundCallback<T> {

    public DefaultCallback(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    public final boolean onResultError(int code, Headers headers, Result.Error error) {
        if (code == 401) {
            return onResultAuthError(code, headers, error);
        } else {
            return onResultOtherError(code, headers, error);
        }
    }

    public boolean onResultAuthError(int code, Headers headers, Result.Error error) {
        AlertDialogUtils.createBuilderWithAutoTheme(getActivity())
                .setMessage(R.string.access_token_out_of_date)
                .setPositiveButton(R.string.relogin, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.startForResult(getActivity());
                    }

                })
                .setNegativeButton(R.string.cancel, null)
                .show();
        return false;
    }

    public boolean onResultOtherError(int code, Headers headers, Result.Error error) {
        ToastUtils.with(getActivity()).show(error.getErrorMessage());
        return false;
    }

    @Override
    public boolean onCallException(Throwable t, Result.Error error) {
        ToastUtils.with(getActivity()).show(error.getErrorMessage());
        return false;
    }

}
