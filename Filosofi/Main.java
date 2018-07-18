public class Main{
    public static void main(String[] args) {
        Table table = new Table(5);
        Philosopher[] philos = new Philosopher[5];

        for(int i = 0; i < philos.length; i++){
            philos[i] = new Philosopher("Philosopher " + i, table, i);
            philos[i].start();
        }

        try{
            for(int i = 0; i < philos.length; i++){
                philos[i].join();
            }   
        }catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }
}