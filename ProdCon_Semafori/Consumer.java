public class Consumer extends Thread{
    private SharedValue sv;

    public Consumer(SharedValue sv){
        this.sv = sv;
    }

    @Override
    public void run(){
        try{
            for(int i = 0; i < 10; i++){
                sv.consume();
                Thread.sleep(1000);
            }
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }
}