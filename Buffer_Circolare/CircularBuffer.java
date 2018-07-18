public class CircularBuffer{
	private int writePos = 0, readPos = 0;
	private int slotsToConsume = 0, bufferSize;
	private int[] buffer;

	public CircularBuffer(int bufferSize){
		this.bufferSize = bufferSize;
		this.buffer = new int[this.bufferSize];
	}

	public synchronized void put(int value) throws InterruptedException{
		while(slotsToConsume == bufferSize){
			System.out.println("Producer must wait");
			wait();
		}

		buffer[writePos] = value;
		System.out.println("Producer puts " + value + " in postition " + writePos);
		writePos = (writePos + 1) % bufferSize;
		slotsToConsume++;
		notifyAll();
	}

	public synchronized void get() throws InterruptedException{
		while(slotsToConsume == 0){
			System.out.println("Consumer must wait");
			wait();
		}

		int value = buffer[readPos];
		System.out.println("Consumer gets " + value + " in position " + readPos);
		readPos = (readPos + 1) % bufferSize;
		slotsToConsume --;
		notifyAll();
	}
}