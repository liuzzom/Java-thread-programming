public class Main {
	public static void main(String[] args){
		Buffer buffer = new SynchBuffer(3);

		Producer producer = new Producer(buffer);
		Consumer consumer1 = new Consumer(buffer, 1);
		Consumer consumer2 = new Consumer(buffer, 2);

		producer.start();
		consumer1.start();
		consumer2.start();

		try{
			producer.join();
			consumer1.join();
			consumer2.join();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}