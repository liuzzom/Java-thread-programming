public class Producer extends Thread{
	private Buffer buffer;

	public Producer(Buffer buffer){
		super("Producer");
		this.buffer = buffer;
	}

	public void run(){
		try{
			for(int i = 1; i <= 4; i++){
				// Producer sleeps for a random time between 0 and 3 seconds
				Thread.sleep( (int)(Math.random()*3000) ); 
				buffer.set(i);
			}
		}
		catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}
}