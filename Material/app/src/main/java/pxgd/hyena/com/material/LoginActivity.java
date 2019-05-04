package pxgd.hyena.com.material;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.material.model.entity.Result;
import pxgd.hyena.com.material.model.storage.LoginShared;
import pxgd.hyena.com.material.presenter.contract.ILoginPresenter;
import pxgd.hyena.com.material.presenter.implement.LoginPresenter;
import pxgd.hyena.com.material.ui.dialog.AlertDialogUtils;
import pxgd.hyena.com.material.ui.dialog.ProgressDialog;
import pxgd.hyena.com.material.ui.listener.NavigationFinishClickListener;
import pxgd.hyena.com.material.ui.util.ThemeUtils;
import pxgd.hyena.com.material.ui.view.ILoginView;
import retrofit2.Call;

public class LoginActivity extends FullLayoutActivity implements ILoginView {

    public static final int REQUEST_DEFAULT = 0;
    private static final int REQUEST_PERMISSIONS_QR_CODE = 0;
    private static final int REQUEST_QR_CODE_LOGIN = 1;
    private static final int REQUEST_GITHUB_LOGIN = 2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_access_token)
    MaterialEditText edtAccessToken;
    private ProgressDialog progressDialog;
    private ILoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        progressDialog = ProgressDialog.createWithAutoTheme(this);
        loginPresenter = new LoginPresenter(this, this);
    }




    public static void startForResult(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivityForResult(intent, requestCode);
    }
    public static void startForResult(@NonNull Activity activity) {
        startForResult(activity, REQUEST_DEFAULT);
    }
    public static boolean checkLogin(@NonNull final Activity activity, final int requestCode) {
        if (TextUtils.isEmpty(LoginShared.getAccessToken(activity))) {
            AlertDialogUtils.createBuilderWithAutoTheme(activity)
                    .setMessage(R.string.need_login_tip)
                    .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startForResult(activity, requestCode);
                        }

                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
            return false;
        } else {
            return true;
        }
    }
    public static boolean checkLogin(@NonNull Activity activity) {
        return checkLogin(activity, REQUEST_DEFAULT);
    }




    @OnClick(R.id.btn_login)
    void onBtnLoginClick() {
        loginPresenter.loginAsyncTask(edtAccessToken.getText().toString().trim());
    }
    @OnClick(R.id.btn_qr_code_login)
    void onBtnQrCodeLoginClick() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ScanQRCodeActivity.requestPermissions(this, REQUEST_PERMISSIONS_QR_CODE);
        } else {
            ScanQRCodeActivity.startForResult(this, REQUEST_QR_CODE_LOGIN);
        }
    }
    @OnClick(R.id.btn_github_login)
    void onBtnGithubLoginClick() {
        /*
        startActivityForResult(
                new Intent(
                        this,
                        CNodeOAuthLoginActivity.class),
                REQUEST_GITHUB_LOGIN
        );
        */
    }










    @Override
    public void onAccessTokenError(@NonNull String message) {

    }
    @Override
    public void onLoginOk(@NonNull String accessToken, @NonNull Result.Login loginInfo) {

    }
    @Override
    public void onLoginStart(@NonNull Call<Result.Login> call) {

    }
    @Override
    public void onLoginFinish() {

    }
}
