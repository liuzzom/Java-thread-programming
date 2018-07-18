public class Main{
    public static void main(String[] args) {
        Canale canale = new Canale();
        int numSender = 2;
        Sender[] senders = new Sender[numSender];
        Receiver receiver = new Receiver("Receiver", canale);

        for(int i = 0; i < numSender; i++){
            senders[i] = new Sender("Sender " + i, canale);
        }

        for(int i = 0; i < numSender; i++){
            senders[i].start();
        }
        receiver.start();

        try{
            for(int i = 0; i < numSender; i++){
                senders[i].join();
            }
            receiver.join();    
        }
        catch(InterruptedException exc){
            exc.printStackTrace();
        }
    }
}