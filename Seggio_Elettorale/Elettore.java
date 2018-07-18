import java.util.Random;

public class Elettore extends Thread{
	private int ruolo; // 0: studente, 1:Personale TAB, 2:Docente
	private int numSeggi;
	private Seggio[] seggi; // l'elettore sa quanti sono i seggi, in modo da poterne scegliere uno

	public Elettore(String name, int ruolo, int numSeggi, Seggio[] seggi){
		super(name);
		this.ruolo = ruolo;
		this.numSeggi = numSeggi;
		this.seggi = seggi;
	}

	public int getRuolo(){
		return this.ruolo;
	}

	@Override
	public void run(){
		boolean entrato;
		try{
			Random rand = new Random();
			// l'elettore prova ad entrare in un seggio scelto a caso
			int seggioScelto;
			// l'elettore tenta fino a quando non entra in un seggio
			do{
				seggioScelto = rand.nextInt(numSeggi); // estrazione di un numero che determina il seggio scelto
				entrato = seggi[seggioScelto].entra();
			}while(!entrato);

			// l'elettore si trova nella coda di attesa per la convalida
			// l'elettore comincia la convalida
			seggi[seggioScelto].iniziaConvalida();

			//simulazione tempo di convalida
			Thread.sleep(rand.nextInt(2000));
			// se la convalida non va a buon fine l'elettore va via
			if(!seggi[seggioScelto].finisciConvalida()){
				return;
			}

			int seggio = seggi[seggioScelto].iniziaVoto();

			// simulazione tempo di voto
			Thread.sleep(rand.nextInt(5000));
			seggi[seggioScelto].finisciVoto(seggio);
		}catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}
}