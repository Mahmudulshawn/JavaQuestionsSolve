import java.util.LinkedList;

public class Main{
    public static void main(String[] args) throws InterruptedException {
        LinkedList<Integer> list = new LinkedList<>();

        UnsafeAdderThread adder = new UnsafeAdderThread(list);
        UnsafeRemoverThread remover = new UnsafeRemoverThread(list);

        adder.start();
        remover.start();
        
        //ene wait kormu ok bara
        adder.join();
        remover.join();

        System.out.println("Final List: " + list);
    }
}