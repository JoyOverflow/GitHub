package pxgd.hyena.com.guanggoo.data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import pxgd.hyena.com.guanggoo.base.NamedThreadFactory;

/**
 *
 * @author mazhuang
 * @date 2017/9/16
 */

public class NetworkTaskScheduler {

    private ExecutorService mExecutor;

    private static class InstanceHolder {
        private static NetworkTaskScheduler sInstance = new NetworkTaskScheduler();
    }

    public static NetworkTaskScheduler getInstance() {
        return InstanceHolder.sInstance;
    }

    private NetworkTaskScheduler() {
        mExecutor = new ThreadPoolExecutor(
                1,
                1,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new NamedThreadFactory(NetworkTaskScheduler.class.getSimpleName())
        );
    }

    public void execute(Runnable runnable) {
        mExecutor.execute(runnable);
    }
}
