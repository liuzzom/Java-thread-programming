import java.util.LinkedList;

public class Rivendita{
    private Biglietteria biglietteria;
    private LinkedList<Integer> clienti;
    private int bigliettiDisponibili;

    public Rivendita(Biglietteria biglietteria, int bigliettiIniziali){
        this.biglietteria = biglietteria;
        this.bigliettiDisponibili = bigliettiIniziali;
        this.clienti = new LinkedList<>();
    }

    // Verifica se il negozio può soddisfare la richiesta del Cliente
    public boolean verificaDisponibilità(int bigliettiRichiesti){
        if(bigliettiDisponibili < bigliettiRichiesti && biglietteria.getBigliettiDisponibili() < bigliettiRichiesti){
            return false;
        }
        return true;
    }

    public synchronized void mettiInCoda(int id){
        String nome = Thread.currentThread().getName();
        System.out.println(nome + " " + id + " è entrato nella biglietteria");
        // Mette il nuovo cliente in coda
        clienti.add(id);
        System.out.println(nome + " " + id + " si è messo in coda");
    }

    public synchronized boolean compraBiglietti(int id, int bigliettiRichiesti){
        String nome = Thread.currentThread().getName();
        try{
            // Se il cliente non è il primo della coda
            while(id != clienti.getFirst()){
                String nomeCliente = Thread.currentThread().getName();
                System.out.println(nomeCliente + " " + id + " va in attesa");
                wait();
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Il cliente è il primo in coda

        if(!verificaDisponibilità(bigliettiRichiesti)){
            // La rivendita non può soddisfare la richiesta del cliente
            // Nemmeno rivolgendosi alla Biglietteria
            // Viene rimosso il cliente dalla coda
            clienti.removeFirst();
            System.out.println("\t\t\t\t" + nome + " " + id + " non può comprare " + bigliettiRichiesti + " biglietti ed esce");    
            notifyAll();
            return false; 
        }

        // Se non ci sono abbastanza biglietti, la Rivendita si rifornisce in modo da soddisfare la richiesta
        if(bigliettiDisponibili < bigliettiRichiesti){
            bigliettiDisponibili += biglietteria.rifornisci(bigliettiRichiesti);
        }

        // Si riduce il numero di biglietti disponiboli
        bigliettiDisponibili -= bigliettiRichiesti;
        // Viene rimosso il cliente dalla coda
        clienti.removeFirst();
        System.out.println("\t\t\t\t" + nome + " " + id + " compra " + bigliettiRichiesti + " biglietti ed esce");
        notifyAll();

        return true;
    }
}