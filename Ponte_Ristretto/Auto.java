public class Auto extends Thread{
	private int verso;
	private Ponte ponte;

	public Auto(String name, int verso, Ponte ponte){
		super(name);
		this.verso = verso;
		this.ponte = ponte;
	}

	public void run(){
		try{
			ponte.entra(verso);
			// Simulazione tempo di attraversamento
			Thread.sleep(2000);
			ponte.esci();
		}catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}
}