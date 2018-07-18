import java.util.Random;

public class Receiver extends Thread{
    private Canale canale;

    public Receiver(String name, Canale canale){
        super(name);
        this.canale = canale;
    }

    public void run(){
        Random r = new Random();

        while(true){
            try{
                canale.receive();
                Thread.sleep(r.nextInt(3000));
            }
            catch(InterruptedException exc){
                exc.printStackTrace();
            }
        }

    }
}