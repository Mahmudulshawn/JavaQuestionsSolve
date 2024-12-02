import java.util.LinkedList;

class UnsafeRemoverThread extends Thread {
    private final LinkedList<Integer> list;

    public UnsafeRemoverThread(LinkedList<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            if (!list.isEmpty()) {
                int removed = list.removeFirst();
                System.out.println("Removed: " + removed);
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

