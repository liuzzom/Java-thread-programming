import java.util.concurrent.Semaphore;

public class ProdCon{
	public static void main(String[] args){
		Coda q = new Coda();

		Consumer consumer = new Consumer(q);
		Producer producer = new Producer(q);
	}
}

class Producer extends Thread{
	Coda queue;

	public Producer(Coda queue){
		super("Tread produttore P");
		this.queue = queue;
		this.start();
	}

	@Override
	public void run(){
		for(int i=0; i<5; i++){
			queue.put(i+1);
		}
	}
}

class Consumer extends Thread{
	Coda queue;

	public Consumer(Coda queue){
		super("Tread consumatore C");
		this.queue = queue;
		this.start();
	}

	@Override
	public void run(){
		for(int i=0; i<5; i++){
			queue.get();
		}
	}
}

// la classe coda contiene il semaforo e le risorse condivise
class Coda{
	// semProducer viene inizializzato ad 1. In questo modo 1 thread può entrare nella sua sezione critica
	static Semaphore semProducer = new Semaphore(1);
	// semConsume viene inizializzato a 0. In questo modo nessun thread attualmente può entrare nella sua sezione critica
	static Semaphore semConsumer = new Semaphore(0);
	int value;

	public Coda(){}

	void put(int n){
		try{
			semProducer.acquire();
			// --- Critic section: start
			this.value = n;
			System.out.println("Producer -> " + value);
			// --- Critic section: end
			semConsumer.release();

		} catch (InterruptedException e ){
			e.printStackTrace();
		}
	}

	void get(){
		try{
			semConsumer.acquire();
			// --- Critic section: start
			System.out.println("Consumer <- " + value);
			// --- Critic section: end
			semProducer.release();

		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}

/*
Due thread scrivono contemporaneamente su un array condiviso, il consumatore deve leggere i valori man mano che vengono prodotti.
Provare anche con due consumatori che devono leggere un valore


Fabbrica che assembla 2 prodotti, che necessita 2 componenti.
I due consumatori accedono ad i depositi che vengono riempiti dai produttori.
Ciascun consumatore inizia a produrre quando ha già ottenuto il materiale necessario.
*/
