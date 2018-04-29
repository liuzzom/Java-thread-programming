public class Primes extends Thread{
	private int number;
	private boolean isPrime;

	public Primes(){}

	public Primes(int number){
		this.number = number;
	}

	public void run(){

		for(int i = 2; i <= number/2; i++){
			if(number%i == 0){
				this.isPrime = false;
				return;
			}
		}
		System.out.println(number+" è primo...");
		this.isPrime = true;
	}

	public static void main(String[] args) throws InterruptedException{
		int primesToFind = 0;
		try{
			primesToFind = Integer.parseInt(args[0]);
		}
		catch(NumberFormatException|ArrayIndexOutOfBoundsException exc){
			System.out.println("Input non valido");
		}

		int primesFound = 0, currentNumber = 2, numThread = 2;
		Primes[] threads = new Primes[numThread];
		int[] numbers = new int[numThread]; 

		while(primesFound < primesToFind){
			for(int i = 0; i < numThread; i++){
				numbers[i] = currentNumber;
				threads[i] = new Primes(numbers[i]);
				threads[i].start();
				currentNumber++;
			}

			for(int i = 0; i < numThread; i++){
				threads[i].join();
				if(threads[i].isPrime){
					primesFound++;
				}
			}

		}
		System.out.println(primesToFind+" "+(currentNumber-1));
	}
}