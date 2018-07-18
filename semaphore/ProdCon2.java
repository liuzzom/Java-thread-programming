import java.util.concurrent.Semaphore;

public class ProdCon2{
	public static void main(String[] args){
		int[] array = new int[10];
		Coda queue = new Coda(array);

		Consumer consumer = new Consumer(queue);
		Producer[] producers = new Producer[2];
		producers[0] = new Producer("Producer1",queue);
		producers[1] = new Producer("Producer2",queue);
	}
}

class Producer extends Thread{
	private Coda queue;

	public Producer(String name, Coda queue){
		super(name);
		this.queue = queue;
		this.start();
	}

	@Override
	public void run(){
		try{
			for(int i=0; i<10; i++){
				System.out.println(Thread.currentThread().getName() + " tries to put " + i);
				queue.put(i);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

class Consumer extends Thread{
	Coda queue;

	public Consumer(Coda queue){
		super("Consumer1");
		this.queue = queue;
		this.start();
	}

	@Override
	public void run(){
		try{
			for(int i=0; i<10; i++){
				queue.get();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
}

class Coda{
	static int index;
	static int[] array;
	static Semaphore semProducer1;
	static Semaphore semProducer2;
	static Semaphore semConsumer;
	static boolean producer1Writing;

	public Coda(int[] array){
		this.array = array;
		this.index = 0;
		semProducer1 = new Semaphore(1);
		semProducer2 = new Semaphore(0);
		semConsumer = new Semaphore(0);
		this.producer1Writing = true;
	}

	public void put(int value){
		try{
			if (producer1Writing){
				semProducer1.acquire();
			} else {
				semProducer2.acquire();
			}

			// -- Critic section begins
			this.array[index] = value;
			index++;
			System.out.println(Thread.currentThread().getName() + " puts " + value);
			// -- Critic section ends
			
			semConsumer.release();
			if (producer1Writing){
				semProducer2.release();
			} else {
				semProducer1.release();
			}
			producer1Writing = (Math.random() >= 0.5);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}

	public void get(){
		try{
			semConsumer.acquire();

			// -- Critic section begins
			System.out.println(Thread.currentThread().getName() + " gets " + array[index]);
			// -- Critic section ends

			// Alternate writing of producers
			if(producer1Writing){
				semProducer2.release();
			} else {
				semProducer1.release();
			}
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}