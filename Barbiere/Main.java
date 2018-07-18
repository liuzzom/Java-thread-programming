public class Main{
	public static void main(String[] args) {
		int numBarbieri = 3;
		int numPosti = 5;
		int numClienti = 10;
		Studio studio = new Studio(numBarbieri, numPosti);
		Cliente[] clienti = new Cliente[numClienti];

		for(int i = 0; i < numClienti; i++){
			clienti[i] = new Cliente("Cliente " + i, studio);
			clienti[i].start();
		}
	}
}