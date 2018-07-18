public class Table{
    // Tiene in conto se le forchette sono occupate
    private boolean[] occupiedForks;
    // Posti a sedere sul tavolo    
    private int seats;
    // Tiene in conto se qualcuno dei filosofi sta aspettando troppo
    private boolean[] tooMuchWaiting;

    public Table(int seats){
        this.seats = seats;
        this.occupiedForks = new boolean[seats];
        this.tooMuchWaiting = new boolean[seats];

        for(int i = 0; i < seats; i++){
            this.occupiedForks[i] = false;
            this.tooMuchWaiting[i] = false;
        }
    }

    // Prende la forchetta sinistra
    public synchronized void takeLeftFork(int seatPosition) throws InterruptedException{
        int forkToTake = ((seatPosition -1) + seats) % seats;
        Philosopher philo = (Philosopher) Thread.currentThread();

        // Verifica se la forchetta è occupata oppure se c'è qualcuno che sta aspettando e quello non è lui
        while(occupiedForks[forkToTake] || (isSomeoneWaiting() && (philo.getWaitingTimes() < 5))){
            System.out.println(Thread.currentThread().getName() + " ha provato a prendere la forchetta " + forkToTake + " ma è occupata");
            // incrementa il numero di volta di attesa
            philo.increaseWaitingTimes();

            // se ha atteso troppo a lungo mette il flag a true
            if(philo.getWaitingTimes() >= 5){
                System.out.println(Thread.currentThread().getName() + " aspetta da troppo tempo");
                tooMuchWaiting[seatPosition] = true;
            }
            wait();
        }

        // Prende le forchetta e mette il relativo flag a true
        System.out.println(Thread.currentThread().getName() + " prende la forchetta sinistra" + forkToTake);
        occupiedForks[forkToTake] = true;
    }

    // Prende la forchetta destra
    public synchronized boolean takeRightFork(int seatPosition){
        int forkToTake = seatPosition % seats;

        // Se la forchetta destra è occupata rilascia la forchetta a sinistra
        if(occupiedForks[forkToTake]){
            System.out.println(Thread.currentThread().getName() + " ha provato a prendere la forchetta " + forkToTake + " ma è occupata, quindi rilascia quella alla sua sinistra");
            releaseLeftFork(seatPosition);
            return false;
        }else{
            System.out.println(Thread.currentThread().getName() + " prende la forchetta destra" + forkToTake);
            // Prende le forchetta e mette il relativo flag a true
            occupiedForks[forkToTake] = true;
            return true;
        }
    }

    public synchronized void releaseLeftFork(int seatPosition){
        int forkToRelease = ((seatPosition -1) + seats) % seats;
        System.out.println(Thread.currentThread().getName() + " ha rilasciato la forchetta " + forkToRelease);        
        occupiedForks[forkToRelease] = false;

        notifyAll();
    }
    
    public synchronized void releaseRightFork(int seatPosition){
        int forkToRelease = seatPosition % seats;
        System.out.println(Thread.currentThread().getName() + " ha rilasciato la forchetta " + forkToRelease);        

        Philosopher philo = (Philosopher) Thread.currentThread();
        philo.resetWaitingTimes();      
        tooMuchWaiting[seatPosition] = false;
        occupiedForks[forkToRelease] = false;
        
        notifyAll();
    }

    public boolean isSomeoneWaiting(){
        for(int i = 0; i < seats; i++){
            if(occupiedForks[i]){
                return true;
            }
        }
        return false;
    }
}