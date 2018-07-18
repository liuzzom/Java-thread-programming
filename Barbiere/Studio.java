import java.util.LinkedList;

public class Studio{
	private boolean[] barbieri; // true: dorme, false: occupato
	private int posti; // posti disponibili in attesa
	private LinkedList<Cliente> clienti; // clienti in coda

	public Studio(int numBarbieri, int numPosti){
		barbieri = new boolean[numBarbieri];
		posti = numPosti;
		clienti = new LinkedList<>();

		for(int i = 0; i < numBarbieri; i++){
			barbieri[i] = true;
		}
	}

	public synchronized boolean entra(){
		Cliente cliente= (Cliente) Thread.currentThread();

		if(clienti.size() >= posti){
			// non ci sono posti disponibili, il cliente esce
			System.out.println(cliente.getName() + " trova lo studio pieno e va via");
			return false;
		}

		// Ci sono dei posti, il cliente viene messo in coda
		clienti.add(cliente);
		System.out.println(cliente.getName() + " trova dei posti disponibili e si mette in attesa");
		return true;
	}

	public synchronized int iniziaTaglio(){
		Cliente cliente= (Cliente) Thread.currentThread();
		int nBarbiere = -1; // indice del barbiere che servirà il cliente
		try{
			// se il cliente non è il prossimo in coda, o trova i barbieri occupati, si mette in attese
			while(cliente != clienti.getFirst() || tuttiOccupati()){
				System.out.println(cliente.getName() + " deve aspettare");
				wait();
			}

			// Il cliente ottiene uno dei barbieri disponibili e lo impegna
			nBarbiere = impegnaBarbiere();
			// il cliente viene rimosso dalla coda dei clienti in attesa
			clienti.removeFirst();
			System.out.println(cliente.getName() + " viene servito dal barbiere " + nBarbiere);
		}catch(InterruptedException exc){
			exc.printStackTrace();
		}

		return nBarbiere;
	}

	public synchronized void finisciTaglio(int barbiere){
		Cliente cliente= (Cliente) Thread.currentThread();
		
		// il cliente libera il barbiere che lo ha servito ed esce
		liberaBarbiere(barbiere);
		System.out.println(cliente.getName() + " ha liberato il barbiere " + barbiere + " ed esce");
		notifyAll();
	}

	private boolean tuttiOccupati(){
		for(int i = 0; i < barbieri.length; i++){
			// verifica se il barbiere è libero
			if(barbieri[i]){
				return false;
			}
		}
		return true;
	}

	private int impegnaBarbiere(){
		for(int i = 0; i < barbieri.length; i++){
			// verifica se il barbiere è libero
			if(barbieri[i]){
				// un barbiere è libero, viene impegnato e si restituisce l'indice
				barbieri[i] = false;
				return i;
			}
		}
		return -1;
	}

	private void liberaBarbiere(int barbiere){
		barbieri[barbiere] = true;
	}
}