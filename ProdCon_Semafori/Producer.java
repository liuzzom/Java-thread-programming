public class Producer extends Thread{
    private SharedValue sv;

    public Producer(SharedValue sv){
        this.sv = sv;
    }

    @Override
    public void run(){
        try {
            for(int i = 0; i < 10; i++){
                sv.produce(i+1);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}