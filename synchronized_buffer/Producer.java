public class Producer extends Thread{
	private Buffer sharedLocation;

	public Producer(Buffer sharedLocation){
		super("Producer");
		this.sharedLocation = sharedLocation;
	}

	@Override
	public void run(){
		try{
			for(int count=1; count<=4; count++){
				Thread.sleep((int) (Math.random()*3001));
				sharedLocation.set(count);
			}
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		System.err.println(super.getName() + " ha terminato.");
	}
}