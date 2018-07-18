public class UnsynchBufferTest{
	public static void main(String[] args) {
		Buffer sharedBuffer = new UnSynchBuffer();

		Producer prod = new Producer(sharedBuffer);
		Consumer con = new Consumer(sharedBuffer);

		prod.start();
		con.start();
	}
}