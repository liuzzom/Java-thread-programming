import java.util.LinkedList;

public class Ponte{
	private int verso;
	private LinkedList<Auto> autoSulPonte;

	public Ponte(){
		this.verso = 0;
		autoSulPonte = new LinkedList<>();
	}

	public synchronized void entra(int verso){
		try{
			Auto auto = (Auto) Thread.currentThread();
			String name = auto.getName();
			while(this.verso != verso && !autoSulPonte.isEmpty()){
				System.out.println(name + " deve attendere prima di entrare sul ponte");
				wait();
			}

			this.verso = verso;
			autoSulPonte.add(auto);
			System.out.println(name + " è entrato sul ponte");
		}catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}

	public synchronized void esci(){
		try{
			Auto auto = (Auto) Thread.currentThread();
			String name = auto.getName();
			while(auto != autoSulPonte.getFirst()){
				System.out.println(name + " deve attendere prima di uscire dal ponte");
				wait();
			}

			autoSulPonte.removeFirst();
			System.out.println(name + " è uscito dal ponte");
			notifyAll();
		}catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}
}