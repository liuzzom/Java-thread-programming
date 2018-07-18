public class SynchBuffer implements Buffer{
	private int buffer = -1;
	// variabile con il quale si tiene conto dello stato della risorsa condivisa
	// la variabile fa anche da condizione per la chiamata al metodo wait
	private int occupiedBuffer = 0;

	public synchronized void set(int val){
		String name = Thread.currentThread().getName();
		while(occupiedBuffer == 1){
			try{
				System.out.println("-- " + name + " tenta di scrivere");
				System.out.println("-- Buffer pieno..." + name + " waits...");
				wait();
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}

		buffer = val;
		++occupiedBuffer;
		System.out.println("-- " + name + " ha scritto " + buffer + " --notify()");
		
		// Send a signal to wake up the other thread
		notify();		// Signal and continue
	}

	public synchronized int get(){
		String name = Thread.currentThread().getName();
		while(occupiedBuffer == 0){
			try{
				System.out.println("-- " + name + " tenta di leggere");
				System.out.println("-- Buffer vuoto..." + name + " waits...");
				wait();
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}

		--occupiedBuffer;
		System.out.println("-- " + name + " ha letto " + buffer + " --notify()");

		// Send a signal to wake up the other thread
		notify();		// Signal and continue
		return buffer;
	}
}
