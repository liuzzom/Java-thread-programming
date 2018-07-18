public class Main{
	public static void main(String[] args) {
		int bufferSize = 4;
		CircularBuffer buffer = new CircularBuffer(bufferSize);
		Producer prod = new Producer(buffer);
		Consumer cons = new Consumer(buffer);

		prod.start();
		cons.start();
	}
}