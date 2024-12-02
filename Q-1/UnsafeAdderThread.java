import java.util.LinkedList;

class UnsafeAdderThread extends Thread {
    private final LinkedList<Integer> list;

    public UnsafeAdderThread(LinkedList<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            list.add(i);
            System.out.println("Added: " + i);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}