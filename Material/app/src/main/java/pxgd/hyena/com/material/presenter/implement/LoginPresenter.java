package pxgd.hyena.com.material.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import okhttp3.Headers;
import pxgd.hyena.com.material.R;
import pxgd.hyena.com.material.model.api.ApiClient;
import pxgd.hyena.com.material.model.api.DefaultCallback;
import pxgd.hyena.com.material.model.entity.Result;
import pxgd.hyena.com.material.presenter.contract.ILoginPresenter;
import pxgd.hyena.com.material.ui.view.ILoginView;
import pxgd.hyena.com.material.util.FormatUtils;
import retrofit2.Call;

public class LoginPresenter implements ILoginPresenter {

    private final Activity activity;
    private final ILoginView loginView;

    public LoginPresenter(@NonNull Activity activity, @NonNull ILoginView loginView) {
        this.activity = activity;
        this.loginView = loginView;
    }

    @Override
    public void loginAsyncTask(final String accessToken) {
        if (!FormatUtils.isAccessToken(accessToken)) {
            loginView.onAccessTokenError(activity.getString(R.string.access_token_format_error));
        } else {
            Call<Result.Login> call = ApiClient.service.accessToken(accessToken);
            loginView.onLoginStart(call);
            call.enqueue(new DefaultCallback<Result.Login>(activity) {

                @Override
                public boolean onResultOk(int code, Headers headers, Result.Login loginInfo) {
                    loginView.onLoginOk(accessToken, loginInfo);
                    return false;
                }

                @Override
                public boolean onResultAuthError(int code, Headers headers, Result.Error error) {
                    loginView.onAccessTokenError(getActivity().getString(R.string.access_token_auth_error));
                    return false;
                }

                @Override
                public void onFinish() {
                    loginView.onLoginFinish();
                }

            });
        }
    }

}
