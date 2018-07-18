import java.util.Random;

public class Main{
    public static void main(String[] args) {
        Random rand = new Random();
        Biglietteria biglietteria = new Biglietteria(500);
        int numRivendite = 5;
        Rivendita[] rivendite = new Rivendita[numRivendite];
        int numClienti = 100;
        Cliente[] clienti = new Cliente[numClienti];

        for(int i = 0; i < numRivendite; i++){
            rivendite[i] = new Rivendita(biglietteria, 100);
        }

        try{
            for(int i = 0; i < numClienti; i++){
                clienti[i] = new Cliente(i, rivendite[rand.nextInt(numRivendite)], rand.nextInt(20)+1);
                clienti[i].start();
            }

            for(int i = 0; i < numClienti; i++){
                clienti[i].join();
            }
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }
}