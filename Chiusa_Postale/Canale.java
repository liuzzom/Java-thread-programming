import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Canale{
    private Semaphore semSender = new Semaphore(1);
	private LinkedList<String> coda = new LinkedList<>();
	private int counterSent = 0;
	private int counterReceived = 0;

    public void send(String message) throws InterruptedException{
        semSender.acquire();
        coda.add(message);
		System.out.println("Message sent:" + message + ":" + counterSent);
		counterSent++;
        semSender.release();
    }

    public void receive() throws InterruptedException{
        if(!coda.isEmpty()){
            String msg = coda.removeFirst();
			System.out.println("Message received:" + msg + ":" + counterReceived);
			counterReceived++;
        }
    }
}