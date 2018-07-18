import java.util.LinkedList;
import java.util.Random;

public class Seggio{
	private boolean commissione; // true: libero, false: occupato 
	private boolean[] cabine; // true: libero, false: occupato 
	private int numCabine; // numero di cabine elettorali
	private int cabineOccupate; // numero di cabine occupate
	private int numElettori; // numero di elettori in attesa nel seggio
	private final int MAX = 10; // numero massimo di elettori in attesa di entrare in cabina
	private LinkedList<Elettore> codaAttesa, codaStudenti, codaTab, codaDocenti;

	public Seggio(int numCabine){
		this.numCabine = numCabine;
		this.numElettori = 0;
		this.commissione = true;
		this.codaAttesa = new LinkedList<>();
		this.codaStudenti = new LinkedList<>();
		this.codaTab = new LinkedList<>();
		this.codaDocenti = new LinkedList<>();
		this.cabine = new boolean[numCabine];
		this.cabineOccupate = 0;

		for(int i = 0; i < numCabine; i++){
			this.cabine[i] = true;
		}
	}

	public synchronized boolean entra(){
		Elettore elettore = (Elettore) Thread.currentThread();

		if(numElettori >=  MAX){
			System.out.println(elettore.getName() + " trova il seggio occupato e prova in un altro");
			return false;
		}

		// l'elettore viene messo nella coda di attesa per la convalida
		codaAttesa.add(elettore);
		numElettori++;
		System.out.println(elettore.getName() + " entra nel seggio e si mette in coda per la convalida");
		return true;
	}

	public synchronized void iniziaConvalida(){
		Elettore elettore = (Elettore) Thread.currentThread();

		// Se l'elettore non è il primo della lista o la commissione è occupata deve attendere
		while(elettore != codaAttesa.getFirst() || !commissione){
			System.out.println(elettore.getName() + " deve attendere per la convalida");
			try{
				wait();
			}catch(InterruptedException exc){
				exc.printStackTrace();
			}
		}

		// l'elettore è il primo della lista. Viene rimosso dalla coda di attesa e impegna la commissione
		codaAttesa.removeFirst();
		commissione = false;
		System.out.println(elettore.getName() + " ha iniziato la convalida");
	}

	public synchronized boolean finisciConvalida(){
		Elettore elettore = (Elettore) Thread.currentThread();
		Random rand = new Random();
		boolean esito = rand.nextBoolean();

		if(!esito){
			System.out.println(elettore.getName() + " non è ammesso alla votazione e va via");
			// la commissione viene liberata per un altro elettore
			commissione = true;
			// dato che l'elettore va via, si decrementa il numero di elettori in attesa
			numElettori--;
			notifyAll();
			return false;
		}

		System.out.println(elettore.getName() + " è ammesso alla votazione");
		// la commissione viene liberata per un altro elettore
		commissione = true;
		// messa in coda in base al ruolo
		if(elettore.getRuolo() == 0){
			codaStudenti.add(elettore);
		}else if(elettore.getRuolo() == 1){
			codaTab.add(elettore);
		}else{
			codaDocenti.add(elettore);
		}
		System.out.println(elettore.getName() + " entra nella propria coda");
		notifyAll();
		return true;
	}

	public synchronized int iniziaVoto(){
		Elettore elettore = (Elettore) Thread.currentThread();
		int nCabina = -1;
		try{
			// varie condizioni di attesa in base al ruolo. Tutti devono aspettare che ci siano cabine libere
			if(elettore.getRuolo() == 0){
				// se lo studente non è il primo della lista deve attendere
				while(elettore != codaStudenti.getFirst() || tutteOccupate()){
					System.out.println(elettore.getName() + " (studente) deve aspettare");
					wait();
				}
			}else if(elettore.getRuolo() == 1){
				// se l'addetto al personale tab non è il primo della sua lista o se ci sono studenti in attesa deve aspettare
				while(elettore != codaTab.getFirst() || !codaStudenti.isEmpty() || tutteOccupate()){
					System.out.println(elettore.getName() + " (personale tab) deve aspettare");
					wait();
				}
			}else{
				// affinchè lo studente possa votare deve essere il primo della lista e le altre liste devono essere vuote
				while(elettore != codaDocenti.getFirst() || !codaStudenti.isEmpty() || !codaTab.isEmpty() || tutteOccupate()){
					System.out.println(elettore.getName() + " (docente) deve aspettare");
					wait();
				}
			}

			// l'elettore riceve una cabina libera e la impegna
			nCabina = impegnaCabina();
			// l'elettore viene rimosso dalla coda di attesa in base al ruolo
			if(elettore.getRuolo() == 0){
				codaStudenti.removeFirst();
				System.out.println(elettore.getName() + " (studente) entra nella cabina " + nCabina);
			}else if(elettore.getRuolo() == 1){
				codaTab.removeFirst();
				System.out.println(elettore.getName() + " (personale tab) entra nella cabina " + nCabina);
			}else{
				codaDocenti.removeFirst();
				System.out.println(elettore.getName() + " (docente) entra nella cabina " + nCabina);
			}
		}catch(InterruptedException exc){
			exc.printStackTrace();
		}

		return nCabina;
	}

	public synchronized void finisciVoto(int nCabina){
		Elettore elettore = (Elettore) Thread.currentThread(); 

		liberaCabina(nCabina);
		if(elettore.getRuolo() == 0){
			System.out.println(elettore.getName() + " (studente) esce dalla cabina " + nCabina);
		}else if(elettore.getRuolo() == 1){
			System.out.println(elettore.getName() + " (personale tab) esce dalla cabina " + nCabina);
		}else{
			System.out.println(elettore.getName() + " (docente) esce dalla cabina " + nCabina);
		}
		notifyAll();
	}

	private boolean tutteOccupate(){
		for(int i = 0; i < numCabine; i++){
			if(cabine[i]){
				return false;
			}
		}

		return true;
	}

	private int impegnaCabina(){
		for(int i = 0; i < numCabine; i++){
			if(cabine[i]){
				// una cabina è libera, viene occupata e si ritorna il numero
				cabine[i] = false;
				return i;
			}
		}

		return -1;
	}

	private void liberaCabina(int nCabina){
		cabine[nCabina] = true;
	}
}