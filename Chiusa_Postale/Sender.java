import java.util.Random;

public class Sender extends Thread{
    private Canale canale;

    public Sender(String name, Canale canale){
        super(name);
        this.canale = canale;
    }

    public void run(){
        Random r = new Random();

        while(true){
            try{
                canale.send("Message from " + Thread.currentThread().getName());
                Thread.sleep(r.nextInt(3000));
            }
            catch(InterruptedException exc){
                exc.printStackTrace();
            }
        }

    }
}