package future.process;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import future.process.util.AbstractFuturesTest;

public class S09_Promises extends AbstractFuturesTest {

	private static final Logger log = LoggerFactory.getLogger(S09_Promises.class);

	private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(10,
			new ThreadFactoryBuilder().setDaemon(true).setNameFormat("FutureOps-%d").build());

	public static <T> CompletableFuture<T> never() {
		return new CompletableFuture<>();
	}

	public static CompletableFuture<String> timeoutAfter(Duration duration) {
		final CompletableFuture<String> promise = new CompletableFuture<>();
		pool.schedule(() -> promise.completeExceptionally(new TimeoutException()), duration.toMillis(), TimeUnit.MILLISECONDS);
		return promise;
	}
	
	public static void main(String[] args) throws InterruptedException {
		CompletableFuture<String> future = realLogic();
		CompletableFuture<String> timeout = timeoutAfter(Duration.ofSeconds(10));
//		future.applyToEitherAsync(timeout, s -> {
//		    System.out.println("Result: " + s);
//		});

	}

	private static CompletableFuture<String> realLogic() throws InterruptedException {
		Thread.sleep(2000);
		CompletableFuture<String> ret = new CompletableFuture<String>();
		//ret.complete("Done");
		return ret;
	}

}
