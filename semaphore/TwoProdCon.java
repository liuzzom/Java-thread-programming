import java.util.concurrent.Semaphore;

public class TwoProdCon {
    public static void main(String[] args) throws Exception{
        SharedResource resource = new SharedResource();
        
        Producer1 prod1 = new Producer1(resource, "Prod 1");
        Producer2 prod2 = new Producer2(resource, "Prod 2");
        Consumer cons = new Consumer(resource, "Cons");

        prod1.start();
        prod2.start();
        cons.start();

    }
}

class SharedResource{
    Semaphore semProducer = new Semaphore(1);
    Semaphore semConsumer = new Semaphore(0);
    private int[] circularBuffer = new int[2];
    private int prodPosition, consPosition;

    public SharedResource(){
        this.prodPosition = 0;
        this.consPosition = 0;
    }

    public void set(int value){
        try{
            semProducer.acquire();
            // critical setion begins
            circularBuffer[prodPosition] = value;
            System.out.println(Thread.currentThread().getName() + " writes " + value + " in position " + prodPosition);
            prodPosition = (prodPosition + 1) % 2;
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
            System.out.println("\t\t\t\t" + Thread.currentThread().getName() + " reads " + circularBuffer[consPosition] + " in position " + consPosition);
            consPosition = (consPosition + 1) % 2;
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

	public Consumer(SharedResource resource, String name){
        super(name);
		this.resource = resource;
	}

	public void run(){
		for(int i = 0; i < 10; i++){
			resource.get();
		}
	}
}

class Producer1 extends Thread{
	private SharedResource resource;

	public Producer1(SharedResource resource, String name){
        super(name);
		this.resource = resource;
	}

	public void run(){
		for(int i = 0; i < 10; i += 2){
			resource.set(i+1);
		}
	}
}

class Producer2 extends Thread{
	private SharedResource resource;

	public Producer2(SharedResource resource, String name){
        super(name);
		this.resource = resource;
	}

	public void run(){
		for(int i = 1; i < 10; i += 2){
			resource.set(i+1);
		}
	}
}