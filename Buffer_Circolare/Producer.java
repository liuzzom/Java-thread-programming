public class Producer extends Thread{
	// shared circular buffer between producer and consumer
	private CircularBuffer buffer;

	public Producer(CircularBuffer buffer){
		super("Producer");
		this.buffer = buffer;
	}

	@Override
	public void run(){
		int i = 0;
		try{
			while(true){
				// buffer method that uses monitor
				buffer.put(i);
				// producer sleeps for a second
				Thread.sleep(1000);
			}
		}catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}
}