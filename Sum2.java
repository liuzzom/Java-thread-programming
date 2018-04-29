import java.io.*;
import java.util.Random;

public class Sum2 extends Thread{
	private int[] numbers;
	private int startPos, endPos, partialSum;

	public Sum2(){

	}

	public Sum2(int[] numbers, int startPos, int endPos){
		this.numbers = numbers;
		this.startPos = startPos;
		this.endPos = endPos;
		this.partialSum = 0;
	}

	public void run(){
		for(int i = startPos; i < endPos; i++ ){
			partialSum += numbers[i];
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException{
		int[] array = new int[10];
		int sum = 0;
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(ir);
		int numThread;
		Random r = new Random();

		System.out.print("Inserire numero di thread da creare:");
		try{
			numThread = Integer.parseInt(in.readLine());	
		}
		catch(NumberFormatException exc){
			System.out.println("Numero non valido. Verranno utilizzati 2 thread");
			numThread = 2;
		}

		for(int i = 0; i < array.length; i++){
			array[i] = r.nextInt(10)+1;
			System.out.print(array[i]+" ");
		}	
		System.out.println("");

		int step = array.length/numThread;
		
		if(array.length%numThread == 0){
			//create and start threads
			Sum2[] threads= new Sum2[numThread];
			for(int i = 0; i < numThread; i++){
				threads[i] = new Sum2(array, step*i, step*(i+1));
				threads[i].start();
			}
			//waiting for threads exit
			for(int i = 0; i < numThread; i++){
				threads[i].join();
				sum += threads[i].partialSum;
			}
			System.out.println("Sum:"+sum);
		}else{
			//create and start threads
			Sum2[] threads= new Sum2[numThread+1];
			for(int i = 0; i < numThread; i++){
				threads[i] = new Sum2(array, step*i, step*(i+1));
				threads[i].start();
			}
			threads[numThread] = new Sum2(array, array.length-(array.length%numThread), array.length);
			threads[numThread].start();
			//waiting for threads exit
			for(int i = 0; i < numThread; i++){
				threads[i].join();
				sum += threads[i].partialSum;
			}
			threads[numThread].join();
			sum += threads[numThread].partialSum;
			System.out.println("Sum:"+sum);
		}

	}
}