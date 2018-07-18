import java.io.*; // per le componenti della pipe
import java.util.*;
// Per la pipe si utilizza PipedWriter e PipedReader

class Sender extends Thread{
	private Random rand = new Random();
	private PipedWriter out;

	public Sender(String name){
		super(name);
		out = new PipedWriter();
	}

	public PipedWriter getPipedWriter(){
		return this.out;
	}

	public void run(){
		try{
			for(char c = 'a'; c <= 'z'; c++){
				System.out.println("Sending "+c);
				out.write(c);
				sleep(rand.nextInt(1000));
			}
			out.close();
			System.out.println("Sender ha chiuso lo stream");
		}
		catch(Exception exc){
			throw new RuntimeException(exc);
		}
	}
}

class Receiver extends Thread{
	private PipedReader in;

	public Receiver(Sender sender, String name) throws IOException{
		super(name);
		in = new PipedReader(sender.getPipedWriter());
	}

	public void run(){
		try{
			int c;
			while((c = in.read()) > 0 && c <= 'z'){
				System.out.println("Read "+ (char)c);
			}
			in.close();
			System.out.println("Receiver ha terminato");
		}
		catch(Exception exc){
			throw new RuntimeException(exc);
		}
	}
}

public class Pipe{
	public static void main(String[] args) throws IOException{
		Sender sender = new Sender("Sender");
		Receiver receiver = new Receiver(sender,"Receiver");

		sender.start();
		receiver.start();
	}
}