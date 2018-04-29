public class Sum extends Thread{
	private int[] numbers;
	private int startPos, endPos, partialSum;

	public Sum(int[] numbers, int startPos, int endPos){
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

	public static void main(String[] args) throws InterruptedException{
		int[] array = new int[10];
		int sum;

		for(int i = 0; i < array.length; i++){
			array[i] = i+1;
			System.out.print(array[i]+" ");
		}	
		System.out.println("");

		Sum thread1 = new Sum(array, 0, array.length/2);
		Sum thread2 = new Sum(array, array.length/2, array.length);

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		sum = thread1.partialSum+thread2.partialSum;

		System.out.println("Sum:"+sum);
	}
}