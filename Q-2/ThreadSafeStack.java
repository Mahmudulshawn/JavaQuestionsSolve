public class ThreadSafeStack<T> {
    private Node<T> top;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public ThreadSafeStack() {
        top = null;
    }

    public synchronized void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        System.out.println(Thread.currentThread().getName() + " pushed: " + data);
    }

    public synchronized T pop() {
        if (isEmpty()) {
            System.out.println(Thread.currentThread().getName() + " attempted to pop but the stack is empty.");
            return null;
        }
        T data = top.data;
        top = top.next;
        System.out.println(Thread.currentThread().getName() + " popped: " + data);
        return data;
    }

    public synchronized boolean isEmpty() {
        return top == null;
    }

    public static void main(String[] args) {
        ThreadSafeStack<Integer> stack = new ThreadSafeStack<>();

        Thread pusher1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                stack.push(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Pusher-1");

        Thread pusher2 = new Thread(() -> {
            for (int i = 5; i < 10; i++) {
                stack.push(i);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Pusher-2");

        // Create threads for popping elements
        Thread popper1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                stack.pop();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Popper-1");

        Thread popper2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                stack.pop();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Popper-2");

        pusher1.start();
        pusher2.start();
        popper1.start();
        popper2.start();
    }
}
