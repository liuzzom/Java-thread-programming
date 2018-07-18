import java.util.concurrent.Semaphore;

public class SimpleSemaphore {
	public static void main(String[] args) throws Exception {
		
		// prototype: new Semaphore(num of permissions, scheduling policy (true: FIFO, false: JVM))
		Semaphore sem = new Semaphore(1,true);

		Thread thA = new Thread(new SynchroPrint(sem, "message from A"));
		Thread thB = new Thread(new SynchroPrint(sem, "message from B"));

		// Start threads
		thA.start();
		thB.start();

		// Join Threads
		thA.join();
		thB.join();
	}
}

class SynchroPrint extends Thread {
	Semaphore sem;
	String message;

	public SynchroPrint(Semaphore sem, String message){
		this.sem = sem;
		this.message = message;
	}

	@Override
	public void run(){
		try{
			System.out.println(Thread.currentThread().getName() + " trying to acquire...");
			sem.acquire(); // P(S)
			System.out.println(Thread.currentThread().getName() + " acquired sem!");
			
			// Critical section: START
			for(int i=0; i<10; i++){
				System.out.println(message + " : " + (i+1));
				Thread.sleep(500);
			}
			// Critical section: END

		} catch (Exception e ){
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + " releases sem...");
		sem.release(); // V(S)
	}
}