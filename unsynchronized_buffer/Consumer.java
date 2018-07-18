public class Consumer extends Thread{
	private Buffer buffer;
	private int sum = 0;

	public Consumer(Buffer buffer){
		super("Consumer");
		this.buffer = buffer;
	}

	public void run(){
		try{
			for(int i = 1; i<= 4; i++){
				Thread.sleep( (int)(Math.random()*3000) );
				this.sum += buffer.get();
			}
			System.out.println(Thread.currentThread().getName() + " read values sum:" + sum);
		}
		catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}
}