
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor implements Executor {
    private ExecutorService executor;

    public ThreadExecutor() {
        int coreCount = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(coreCount);
    }

    public void executeTask(Runnable task) {
        executor.execute(task);
    }

    @Override
    public void execute(@NonNull final Runnable command) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                command.run();
            }
        }).start();
    }
}

public class UIExecutor implements Executor {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
        handler.post(command);
    }
}
