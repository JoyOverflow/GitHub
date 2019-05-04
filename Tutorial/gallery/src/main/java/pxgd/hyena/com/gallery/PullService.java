package pxgd.hyena.com.gallery;
import android.app.IntentService;
import android.content.Intent;

/**

 */
public class PullService extends IntentService {

    private static final String TAG = "PollService";

    /**
     * 构造
     */
    public PullService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
