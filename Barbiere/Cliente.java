import java.util.Random;

public class Cliente extends Thread{
	private Studio studio; // astrazione dello studio dei barbieri

	public Cliente(String name, Studio studio){
		super(name);
		this.studio = studio;
	}

	public void run(){
		// tentativo di entrare nello studio. Se lo studio ha dei posti liberi si mette in coda per un baribiere

		if(!studio.entra()){
			// lo studio non ha posti liberi
			return;
		}

		// il cliente inizia il taglio di capelli, impegando un barbiere libero e togliendosi dalla coda
		int barbiere = studio.iniziaTaglio();
		// simulazione tempo del taglio di capelli
		try{
			Random rand = new Random();
			Thread.sleep(rand.nextInt(5000));
		}catch(InterruptedException exc){
			exc.printStackTrace();
		}
		// il cliente disimpegna il barbiere ed esce
		studio.finisciTaglio(barbiere);
	}
}