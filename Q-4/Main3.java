import java.util.concurrent.*;

public class Main3 {
    public static void main(String[] args) throws InterruptedException {
        int poolSize = 5; // Number of resources in the pool
        int numThreads = 20; // Number of threads trying to acquire and release resources

        ResourcePool pool = new ResourcePool(poolSize);

        // Create and start a large number of threads
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executor.submit(new ResourceUser(pool));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES); // Wait for all threads to finish
    }
}