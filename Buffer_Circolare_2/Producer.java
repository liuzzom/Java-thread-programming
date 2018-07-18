import java.util.Random;

public class Producer extends Thread{
    // Buffer Circolare Condiviso gestito dal monitor
    private SharedCircularBuffer circularBuffer;
    // Numero di produttori
    private int prodNum;
    private Random rand = new Random();

    public Producer(String name, SharedCircularBuffer circularBuffer, int prodNum){
        super(name);
        this.circularBuffer = circularBuffer;
        this.prodNum = prodNum;
    }

    public void run(){
        try{
            for(int i = 0; i < 10/prodNum; i++){
                circularBuffer.set(rand.nextInt(10)+1);
                Thread.sleep(1000);   
            }
        }
        catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }
}