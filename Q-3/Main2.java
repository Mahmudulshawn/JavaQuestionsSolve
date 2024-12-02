public class Main2 {
    public static void main(String[] args) {
        SynchronizedStack stack = new SynchronizedStack(10);

        Thread producerThread = new Thread(new Producer(stack));
        Thread consumerThread = new Thread(new Consumer(stack));

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
