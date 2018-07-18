public class SharedBufferTest{
	public static void main(String... args){
		Buffer sharedLocation = new SynchBuffer();

		Producer producer = new Producer(sharedLocation);
		Consumer consumer = new Consumer(sharedLocation);

		producer.start();
		consumer.start();

		try{
			producer.join();
			consumer.join();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}