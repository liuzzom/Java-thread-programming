public class ThreadCreation extends Thread{

	public void run(){
		System.out.println("Thread creato in esecuzione");
		try{
			for(int i = 5; i > 0; i--){
				System.out.println(i);
				Thread.sleep(1000);
			}
		System.out.println("Il Thread creato ha finito");	
		}
		catch(InterruptedException exc){
			System.out.println("Thread creato interrotto");
		}
	}

	public static void main(String[] args) {
		System.out.println("Thread Principale in esecuzione");
		
		ThreadCreation thread1 = new ThreadCreation();
		
		try{
			thread1.start();
			thread1.join();
			for(int i = 1; i < 6; i++){
				System.out.println(i);
				sleep(1000);
			}
			System.out.println("Il Thread Principale ha finito");
		}
		catch(InterruptedException exc){
			System.out.println("Thread Principale interrotto");
		}
	}
}