public class Consumer extends Thread{
	private Buffer sharedLocation;

	public Consumer(Buffer sharedLocation){
		super("Consumer");
		this.sharedLocation = sharedLocation;
	}

	@Override
	public void run(){
		int sum = 0;
		try{
			for(int count = 1; count <=4; count++){
				Thread.sleep((int)(Math.random()*3001));
				sum+= sharedLocation.get();
			}
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		System.err.println(super.getName() + " ha terminato, totale: " + sum);
	}
}