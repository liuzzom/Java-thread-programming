import java.io.*;
import java.util.Random;

public class Max extends Thread{
	private int[] numbers;
	private int startPos, endPos, partialMax;

	public Max(int[] numbers, int startPos, int endPos){
		this.numbers = numbers;
		this.startPos = startPos;
		this.endPos = endPos;
		this.partialMax = -1;
	}

	public void run(){

		for(int i = startPos; i < endPos; i++){
			if(this.partialMax < numbers[i]){
				this.partialMax = numbers[i];
			}
		}
		return;
	}

	public static void main(String[] args) throws IOException, InterruptedException{
		int[] array = new int[10];
		Random r = new Random();
		int numThread = 2;
		int max = -1;
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(ir);

		System.out.print("Inserire numero thread da utilizzare:");
		try{
			numThread = Integer.parseInt(in.readLine());
		}
		catch(NumberFormatException exc){
			System.out.println("Input non valido, verrano utilizzati 2 thread (opzione di default)");
		}

		for(int i = 0; i < array.length; i++){
			array[i] = r.nextInt(10)+1;
			System.out.print(array[i]+" ");
		}
		System.out.println("");

		int step = array.length/numThread;

		if(array.length%numThread == 0){
			//creazione thread e start
			Max[] threads = new Max[numThread];
			for(int i = 0; i < numThread; i++){
				threads[i] = new Max(array, step*i, step*(i+1));
				threads[i].start();
			}
			//join e confronto 
			for(int i = 0; i < numThread; i++){
				threads[i].join();
				if(max < threads[i].partialMax){
					max = threads[i].partialMax;
				}
			}
			System.out.println("Max:"+max);
		}else{
			//creazione thread e start
			Max[] threads = new Max[numThread+1];
			for(int i = 0; i < numThread; i++){
				threads[i] = new Max(array, step*i, step*(i+1));
				threads[i].start();
			}
			threads[numThread] = new Max(array, array.length-(array.length%numThread), array.length);
			threads[numThread].start();
			//join e confronto 
			for(int i = 0; i < numThread; i++){
				threads[i].join();
				if(max < threads[i].partialMax){
					max = threads[i].partialMax;
				}
			}
			threads[numThread].join();
			if(max < threads[numThread].partialMax){
				max = threads[numThread].partialMax;
			}
			System.out.println("Max:"+max);
		}

	}

}