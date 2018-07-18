public class Main{
    public static void main(String[] args) {
        SharedValue sv = new SharedValue();

        Producer p = new Producer(sv);
        Consumer c = new Consumer(sv);

        p.start();
        c.start();
    }
}