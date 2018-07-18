import java.util.Random;

public class Main{
	public static void main(String[] args) {
		Ponte ponte = new Ponte();
		int numAuto = 10;
		Auto[] auto = new Auto[numAuto];
		Random rand = new Random();

		for(int i = 0; i < numAuto; i++){
			auto[i] = new Auto("auto " + i, rand.nextInt(2), ponte);
		}

		for(int i = 0; i < numAuto; i++){
			auto[i].start();
		}
	}
}