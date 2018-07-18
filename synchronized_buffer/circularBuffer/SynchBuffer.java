public class SynchBuffer implements Buffer {
	private int size;
	private int writerPosition;
	private int readerPosition;
	private int toBeConsumed;
	private int[] array;

	public SynchBuffer(int size){
		this.size = size;
		array = new int[size];
		writerPosition = 0;
		readerPosition = 0;
		toBeConsumed = 0;
	}

	public synchronized void write(int val){
		String name = Thread.currentThread().getName();
		while(toBeConsumed == size){
			System.out.println(name + " tries to write but waits");

			// put the writer in the wait-set
			try{wait();}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		array[writerPosition++] = val;

		toBeConsumed++;
		writerPosition %= size;

		notifyAll();
	}

	public synchronized int read(){
		String name = Thread.currentThread().getName();
		while(toBeConsumed == 0){
			System.out.println(name + " tries to read but waits");

			// put the reader in the wait-set
			try{wait();}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}

		int val = array[readerPosition++];
		
		toBeConsumed--;
		readerPosition %= size;

		notifyAll();
		return val;
	} 
}