import java.util.Random;
import java.util.concurrent.Semaphore;

public class RandomProdCon{
    public static void main(String[] args) {
		SharedResource shared = new SharedResource();
		
		Producer producer = new Producer(shared);
		Consumer consumer = new Consumer(shared);

		producer.start();
		consumer.start();
    }
}

class SharedResource{
	Semaphore semProducer = new Semaphore(1);
	Semaphore semConsumer = new Semaphore(0);
	private int value;

	public void set(int value){
		try{
			semProducer.acquire();
			// critical section begins
			this.value = value;
			System.out.println("Producer writes " + this.value);
			Thread.sleep(1000);
			// critical section ends
			semConsumer.release();
		}
		catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}

	public void get(){
		try{
			semConsumer.acquire();
			// critical section begins
			System.out.println("Consumer gets " + this.value);
			Thread.sleep(1000);
			// critical section ends
			semProducer.release();
		}
		catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}
}

class Consumer extends Thread{
	private SharedResource resource;

	public Consumer(SharedResource resource){
		this.resource = resource;
	}

	public void run(){
		for(int i = 0; i < 5; i++){
			resource.get();
		}
	}
}

class Producer extends Thread{
	private SharedResource resource;
	private Random rand = new Random();

	public Producer(SharedResource resource){
		this.resource = resource;
	}

	public void run(){
		for(int i = 0; i < 5; i++){
			resource.set(rand.nextInt(10)+1);
		}
	}
}