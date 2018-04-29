public class ThreadExist{
	public static void main(String[] args) {
		Thread thread = Thread.currentThread(); //indirizzo dell'oggetto Thread attualmente in esecuzione
		thread.setName("Thread Principale");
		thread.setPriority(10); // 1:priorità massima, 10:priorità minima, 5:default.

		System.out.println("Thread in esecuzione:"+thread); // richiama la toString della classe Thread

		try{
			for(int i = 5; i > 0; i--){
				System.out.println(i);
				thread.sleep(1000); // aspetta il tempo in input, definito in millisecondi
			}
		}
		catch(InterruptedException exc){
			System.out.println("Thread Interrotto");
		}
	}
}