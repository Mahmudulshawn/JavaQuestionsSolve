import java.util.Stack;

class SynchronizedStack {
    private Stack<Integer> stack;
    private final int maxSize;

    public SynchronizedStack(int maxSize) {
        this.stack = new Stack<>();
        this.maxSize = maxSize;
    }

    // Synchronized push method
    public synchronized void push(int item) {
        while (stack.size() >= maxSize) {
            try {
                wait(); // Wait until there is space in the stack
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        stack.push(item);
        System.out.println("Produced: " + item);
        notifyAll(); // Notify any waiting threads (consumer)
    }

    // Synchronized pop method
    public synchronized int pop() {
        while (stack.isEmpty()) {
            try {
                wait(); // empty thakle kare bar kormu tui i ko
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        int item = stack.pop();
        System.out.println("Consumed: " + item);
        notifyAll(); // Notify any waiting threads (producer)
        return item;
    }
}

// Producer thread class
class Producer implements Runnable {
    private SynchronizedStack stack;

    public Producer(SynchronizedStack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            int num = (int) (Math.random() * 100) + 1;
            stack.push(num);
            try {
                Thread.sleep((long) (Math.random() * 150)); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Consumer thread class
class Consumer implements Runnable {
    private SynchronizedStack stack;

    public Consumer(SynchronizedStack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            stack.pop();
            try {
                Thread.sleep((long) (Math.random() * 150)); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

