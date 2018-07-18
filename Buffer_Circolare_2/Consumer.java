public class Consumer extends Thread{
    // Buffer Circolare Condiviso gestito dal monitor
    private SharedCircularBuffer circularBuffer;

    public Consumer(String name, SharedCircularBuffer circularBuffer){
        super(name);
        this.circularBuffer = circularBuffer;
    }

    public void run(){
        for(int i = 0; i < 10; i++){
            try{
                System.out.println("\t\t\t\t" + Thread.currentThread().getName() + " legge " + circularBuffer.get());
                Thread.sleep(1000);
            }
            catch(InterruptedException exc){
                exc.printStackTrace();
            }
        }
    }

}