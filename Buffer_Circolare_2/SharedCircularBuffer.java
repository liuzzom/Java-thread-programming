public class SharedCircularBuffer{
    private int[] circularBuffer;
    private int writePosition, readPosition;
    // mantains count of occupied slots
    private int occupiedSlots;

    public SharedCircularBuffer(int size){
        this.circularBuffer = new int[size];
        this.writePosition = 0;
        this.readPosition = 0;
        this.occupiedSlots = 0;
        
    }

    public synchronized void set(int value){
        try{
            while(occupiedSlots ==  circularBuffer.length){
                System.out.println("Buffer pieno. " + Thread.currentThread().getName() + " va in attesa");
                wait();
            }
        }
        catch(InterruptedException exc){
            exc.printStackTrace();
        }

        circularBuffer[writePosition] = value;
        System.out.println(Thread.currentThread().getName() + " ha scritto " + value + " in posizione " + writePosition);
        
        occupiedSlots++;
        writePosition = (writePosition + 1) % circularBuffer.length;
        notify();
    }

    public synchronized int get(){
        try{
            while(occupiedSlots == 0){
                System.out.println(Thread.currentThread().getName() + " ha letto tutti i valori disponibili");
                wait();
            }
        }
        catch(InterruptedException exc){
            exc.printStackTrace();
        }

        int value = circularBuffer[readPosition];
        occupiedSlots--;
        readPosition = (readPosition + 1) % circularBuffer.length;
        notify();
        return value;
    }
}