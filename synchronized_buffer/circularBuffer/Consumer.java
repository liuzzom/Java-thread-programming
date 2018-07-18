public class Consumer extends Thread {
	private Buffer buffer;
	private int readVal;

	public Consumer(Buffer buffer, int index){
		super("Consumer" + index);
		this.buffer = buffer;
	}

	@Override
	public void run(){
		for(int i=0; i<5; i++){
			readVal = buffer.read();
			System.out.println(getName() + " reads " + readVal);
		}
		System.out.println(getName() + " ha finito");
	}
}