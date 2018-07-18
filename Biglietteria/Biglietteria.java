public class Biglietteria{
    private int bigliettiDisponibili;

    public Biglietteria(int biglietti){
        this.bigliettiDisponibili = biglietti;
    }

    public int getBigliettiDisponibili(){
        return this.bigliettiDisponibili;
    }

    public int rifornisci(int biglietti){
        this.bigliettiDisponibili -= biglietti;
        return biglietti;
    }
}