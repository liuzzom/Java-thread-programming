import java.util.Random;

public class Philosopher extends Thread{
    // Tavolo che fa da monitor e gestisce le forchette e i metodi per prenderle e rilasciarle
    private Table table;
    // Posto a sedere che determina quali forchette deve prendere
    private int seatPosition;
    private Random rand = new Random();
    // Volte che ha aspettato senza mangiare.
    private int waitingTimes;

    public Philosopher(String name, Table table, int seatPosition){
        super(name);
        this.table = table;
        this.seatPosition = seatPosition;
        this.waitingTimes = 0;
    }

    // Incrementa le volte in cui è in attesa
    public void increaseWaitingTimes(){
        this.waitingTimes++;
    }

    public int getWaitingTimes(){
        return this.waitingTimes;
    }

    // Resetta le volte in cui aspetta dopo aver mangiato
    public void resetWaitingTimes(){
        this.waitingTimes = 0;
    }

    public void run(){
        try{
            while(true){
                think();
                // Prende la forchetta sinistra
                table.takeLeftFork(seatPosition);
                // Se la forchetta destra è libera mangia e rilascia le forchette
                if(table.takeRightFork(seatPosition)){
                    eat();
                    table.releaseLeftFork(seatPosition);
                    table.releaseRightFork(seatPosition);
                }
            }
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }

    public void think(){
        try{
            System.out.println(Thread.currentThread().getName() + " Pensa");
            Thread.sleep(rand.nextInt(3000));
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }

    public void eat(){
        try{
            System.out.println(Thread.currentThread().getName() + " Mangia");
            Thread.sleep(rand.nextInt(3000));
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }
}