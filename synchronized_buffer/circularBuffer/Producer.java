public class Producer extends Thread {
	private Buffer buffer;

	public Producer(Buffer buffer){
		super("Producer");
		this.buffer = buffer;
	}

	@Override
	public void run(){
		for(int i=0; i<10; i++){
			buffer.write(i);
			System.out.println(getName() + " writes " + i);
		}
		System.out.println(getName() + " ha finito");
	}
}