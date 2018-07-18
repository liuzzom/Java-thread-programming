public class Cliente extends Thread{
    private int id;
    private Rivendita rivendita;
    private int bigliettiRichiesti;

    public Cliente(int id, Rivendita rivendita, int bigliettiRichiesti){
        super("Cliente");
        this.id = id;
        this.rivendita = rivendita;
        this.bigliettiRichiesti = bigliettiRichiesti;
    }

    @Override
    public void run(){
        // Il cliente entra nella Rivendita e si mette in coda si mette in coda
        rivendita.mettiInCoda(id);
        
        try{
            // Simula il tempo di attesa in coda
            Thread.sleep(2000);
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }

        rivendita.compraBiglietti(id, bigliettiRichiesti);
    }
}