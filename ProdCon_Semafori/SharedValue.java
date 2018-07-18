import java.util.concurrent.Semaphore;

public class SharedValue{
    private Semaphore semProducer = new Semaphore(1);
    private Semaphore semConsumer = new Semaphore(0);
    private int value;

    public void produce(int value){
        try{
            semProducer.acquire();
            this.value = value;
            System.out.println("Producer puts " + this.value);
            semConsumer.release();
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }

    public void consume(){
        try{
            semConsumer.acquire();
            System.out.println("Consumer gets " + this.value);
            semProducer.release();
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }
}