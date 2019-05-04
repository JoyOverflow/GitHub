package pxgd.hyena.com.material.model.api;


import okhttp3.Headers;
import pxgd.hyena.com.material.model.entity.Result;

public interface CallbackLifecycle<T extends Result> {

    boolean onResultOk(int code, Headers headers, T result);

    boolean onResultError(int code, Headers headers, Result.Error error);

    boolean onCallCancel();

    boolean onCallException(Throwable t, Result.Error error);

    void onFinish();

}
