public class Consumer extends Thread{
	private CircularBuffer buffer;

	public Consumer(CircularBuffer buffer){
		super("Consumer");
		this.buffer = buffer;
	}

	@Override
	public void run(){
		try{
			while(true){
				buffer.get();
				Thread.sleep(1000);
			}
		}catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}
}