import java.util.concurrent.*;

class ResourcePool {
    private final BlockingQueue<Object> pool;

    public ResourcePool(int size) {
        // BlockingQueue will automatically handle waiting for available resources
        pool = new LinkedBlockingQueue<>(size);
        
        // Initialize the pool with resources
        for (int i = 0; i < size; i++) {
            pool.offer(new Object()); // Placeholder resource (just a simple object)
        }
    }

    // Acquire a resource (blocks until a resource is available)
    public void acquire() throws InterruptedException {
        pool.take(); // Block until a resource is available
        System.out.println(Thread.currentThread().getName() + " acquired a resource.");
    }

    // Release a resource back to the pool
    public void release() {
        pool.offer(new Object()); // Return the resource to the pool
        System.out.println(Thread.currentThread().getName() + " released a resource.");
    }
}

class ResourceUser implements Runnable {
    private final ResourcePool pool;

    public ResourceUser(ResourcePool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            pool.acquire(); // Try to acquire a resource
            Thread.sleep((long) (Math.random() * 1000)); // Simulate work with the resource
            pool.release(); // Release the resource after work is done
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


