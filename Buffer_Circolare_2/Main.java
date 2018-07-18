public class Main{
    public static void main(String[] args){
        int size = 4;
        int prodNum = 2;
        SharedCircularBuffer circularBuffer = new SharedCircularBuffer(size);

        Producer prod1 = new Producer("Producer 1", circularBuffer, prodNum);
        Producer prod2 = new Producer("Producer 2", circularBuffer, prodNum);
        Consumer cons = new Consumer("Consumer", circularBuffer);

        prod1.start();
        prod2.start();
        cons.start();

        try{
            prod1.join();
            prod2.join();
            cons.join();
        }
        catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }
}