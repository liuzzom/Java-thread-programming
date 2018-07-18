public class PonteRistretto{
    public static void main(String[] args) {
        Ponte ponte = new Ponte();
        int nAuto = 10;
        Auto[] auto = new Auto[nAuto];
    
        for(int i = 0; i < nAuto; i++){
            auto[i] = new Auto(ponte, Math.round(Math.random()));
            auto[i].start();
        }   
    }
}

class Auto extends Thread{
    private Ponte ponte;
    private double direction;

    public Auto(Ponte ponte, double direction){
        this.ponte = ponte;
        this.direction = direction;
    }

    public void run(){
        try{
            System.out.println(Thread.currentThread().getName() + " tenta di attraversare il ponte in direzione:" + this.direction);
            ponte.entra(this.direction);
            System.out.println(Thread.currentThread().getName() + " è entrato nel ponte");
            sleep(2000); 
            ponte.esce();
            System.out.println(Thread.currentThread().getName() + " è uscito dal ponte");
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }
}

class Ponte{
    private double direction = 0;
    private int numAuto = 0;

    public synchronized void entra(double direction){
        try{
            while(this.direction != direction && this.numAuto != 0){
                System.out.println(Thread.currentThread().getName() + " va in attesa");
                wait();
            }
        }
        catch(InterruptedException exc){
            exc.printStackTrace();
        }

        this.direction = direction;
        numAuto++;
    }

    public synchronized void esce(){
        numAuto--;

        if(numAuto == 0){
            notifyAll();
        }
    }
}